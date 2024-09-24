package org.sparta.outsourcingproject.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.NotFoundException;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreCreateRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreListRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreUpdateRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreCreateResponseDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreGetResponseDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreResponseDto;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.store.enums.StoreOperationStatus;
import org.sparta.outsourcingproject.domain.store.enums.StoreStatus;
import org.sparta.outsourcingproject.domain.store.exception.StoreClosedException;
import org.sparta.outsourcingproject.domain.store.exception.StoreNotFoundException;
import org.sparta.outsourcingproject.domain.store.exception.StoreShutdownException;
import org.sparta.outsourcingproject.domain.store.exception.TooManyStoresRegisteredException;
import org.sparta.outsourcingproject.domain.store.repository.StoreRepository;
import org.sparta.outsourcingproject.domain.user.Authority;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.sparta.outsourcingproject.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    // 가게 등록 메서드
    public StoreCreateResponseDto createStore(StoreCreateRequestDto requestDto, Long userId) {
        List<Store> stores = storeRepository.findByUserId(userId); // 해당 유저의 SHUTDOWN(폐업)인 아닌 모든 가게 목록 가져오기

        // 로그 추가: stores 리스트 크기와 내용 출력
        log.info("유저 ID가 {}인 유저는 현재 가게를 {}개 가지고 있습니다." , userId, stores.size());
        if (stores.size() >= 3) {
            throw new TooManyStoresRegisteredException(ErrorCode.TOO_MANY_STORE_REGISTERED);
        }

        User user = userService.findUser(userId);

        // 등록할 가게 엔티티 생성
        Store newStore = Store.createStore(requestDto, user);

        newStore.updateOperationStatus();  // 현재 시간으로 상태(개점/마감) 업데이트
        Store savedStore = storeRepository.save(newStore); // 가게 등록

        return StoreCreateResponseDto.of(savedStore);

    }

    // 가게 단건 조회 메서드
    public StoreGetResponseDto getStore(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new StoreNotFoundException(ErrorCode.STORE_NOT_FOUND));

        if (store.getOperationStatus() == StoreOperationStatus.SHUTDOWN) {
            throw new StoreShutdownException(ErrorCode.STORE_ALREAY_SHUTDOWN);
        }

        store.updateAverageRating(); // 가게 평균 평점 최신화
        store.updateOperationStatus();  // 현재 시간으로 상태(개점/마감) 업데이트

        return StoreGetResponseDto.of(store);
    }

    // 가게 다건 조회 메서드
    public List<StoreResponseDto> getStores(StoreListRequestDto requestDto) {

        List<Store> findStoreList = new ArrayList<>();

        storeRepository.findAll().stream()
                .filter(store -> store.getName().contains(requestDto.getName()) && store.getOperationStatus() != StoreOperationStatus.SHUTDOWN) // 가게 이름에 입력받은 이름 포함하고 있고 폐업 X
                .forEach(store -> {
                    store.updateOperationStatus(); // 현재 시각으로 가게의 개점/마감 상태 업데이트
                    store.updateAverageRating(); // 평점 업데이트
                    findStoreList.add(store); // 리스트에 추가

                });

        return findStoreList.stream()
                .map(StoreResponseDto::of)
                .collect(Collectors.toList());
    }

    // 가게 정보 수정 메서드
    public StoreGetResponseDto updateStoreInfo(StoreUpdateRequestDto requestDto, Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new StoreNotFoundException(ErrorCode.STORE_NOT_FOUND));

        store.updateStoreInfo(requestDto); // 더티 체킹으로 가게 정보 수정
        return StoreGetResponseDto.of(store);
    }

    // 가게 폐업으로 전환 메서드
    public void setShutdownStore(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new StoreNotFoundException(ErrorCode.STORE_NOT_FOUND));

        if (store.getOperationStatus() == StoreOperationStatus.SHUTDOWN) {
            throw new StoreShutdownException(ErrorCode.STORE_ALREAY_SHUTDOWN);
        }
        store.setStoreShutdown(); // 가게 폐업으로 상태 전환
    }

    public Store findStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreNotFoundException(ErrorCode.STORE_NOT_FOUND));

        store.updateOperationStatus(); // 현재 시간으로 가게 마감/영업 상태 업데이트

//         현재 가게가 폐업인지 아닌지 확인
        if (store.getOperationStatus() == StoreOperationStatus.SHUTDOWN) {
            throw new StoreShutdownException(ErrorCode.STORE_ALREAY_SHUTDOWN);
        }

        return store;
    }

}
