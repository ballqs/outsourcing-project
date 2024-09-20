package org.sparta.outsourcingproject.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreCreateRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreListRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreUpdateRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreCreateResponseDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreGetResponseDto;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.store.enums.StoreOperationStatus;
import org.sparta.outsourcingproject.domain.store.repository.StoreRepository;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.sparta.outsourcingproject.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
//    private final UserService userService;

    // 가게 등록 메서드
//    @Transactional
//    public StoreCreateResponseDto createStore(StoreCreateRequestDto requestDto, Long userId) {
//        List<Store> stores = storeRepository.findByUserId(userId); // 해당 유저의 SHUTDOWN(폐업)인 아닌 모든 가게 목록 가져오기
//
//        if (stores.size() > 3) {
//            throw new IllegalArgumentException("한 명당 가게를 3개까지만 운영할 수 있습니다");
//        }
//
//        User user = userService.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 갖는 유저를 찾을 수 없습니다."));
//
//
//        // ------- 유저가 사장이 아니라면 예외 발생 코드 추가 -----------
//
//
//        // 등록할 가게 엔티티 생성
//        Store newStore = Store.createStore(
//                requestDto.getName(),
//                requestDto.getCategory(),
//                requestDto.getTel(),
//                requestDto.getOpenTime(),
//                requestDto.getCloseTime(),
//                requestDto.getMinPrice(),
//                requestDto.getAddress(),
//                requestDto.getRating(),
//                user,
//                requestDto.getStoreOperationStatus()
//        );
//
//        newStore.updateOperationStatus();  // 현재 시간으로 상태(개점/마감) 업데이트
//        Store savedStore = storeRepository.save(newStore); // 가게 등록
//
//        return StoreCreateResponseDto.of(savedStore);
//
//    }

    // 가게 단건 조회 메서드
    public StoreGetResponseDto getStore(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가지는 가게는 존재하지 않습니다."));

        if (store.getOperationStatus() == StoreOperationStatus.SHUTDOWN) {
            throw new IllegalArgumentException("폐업하여 이제 존재하지 않는 가게입니다");
        }

        store.updateAverageRating(); // 가게 평균 평점 최신화
        store.updateOperationStatus();  // 현재 시간으로 상태(개점/마감) 업데이트
        return StoreGetResponseDto.of(store);
    }

    // 가게 다건 조회 메서드
    public List<StoreCreateResponseDto> getStores(StoreListRequestDto requestDto) {

        List<Store> findStoreList = new ArrayList<>();

        storeRepository.findAll().stream()
                .filter(store -> store.getName().contains(requestDto.getName())&& store.getOperationStatus() != StoreOperationStatus.SHUTDOWN) // 가게 이름에 입력받은 이름 포함하고 있고 폐업 X
                .forEach(store -> {
                    store.updateOperationStatus(); // 현재 시각으로 가게의 개점/마감 상태 업데이트
                    store.updateAverageRating(); // 평점 업데이트
                    findStoreList.add(store); // 리스트에 추가
                });

        return findStoreList.stream()
                .map(StoreCreateResponseDto::of)
                .collect(Collectors.toList());
    }

    // 가게 정보 수정 메서드
    @Transactional
    public StoreGetResponseDto updateStoreInfo(StoreUpdateRequestDto requestDto, Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 갖는 가게를 찾을 수 없습니다."));

        store.updateStoreInfo(requestDto); // 더티 체킹으로 가게 정보 수정
        return StoreGetResponseDto.of(store);
    }

    // 가게 폐업으로 전환 메서드
    @Transactional
    public void setShutdownStore(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다."));

        if (store.getOperationStatus() == StoreOperationStatus.SHUTDOWN) {
            throw new IllegalArgumentException("이미 폐업한 가게입니다.");
        }
        store.setStoreShutdown(); // 가게 폐업으로 상태 전환
    }

}
