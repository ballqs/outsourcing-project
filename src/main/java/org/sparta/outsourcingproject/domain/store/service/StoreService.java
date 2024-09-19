package org.sparta.outsourcingproject.domain.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreCreateRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreCreateResponseDto;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.store.repository.StoreRepository;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.sparta.outsourcingproject.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    @Transactional
    public StoreCreateResponseDto saveStore(StoreCreateRequestDto requestDto, Long userId) {

        List<Store> stores = storeRepository.findByUserId(userId); // 해당 유저의 모든 가게 목록 가져오기

        if (stores.size() > 3) {
            throw new IllegalArgumentException("한 명당 가게를 3개까지만 등록할 수 있습니다");
        }

        User user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 갖는 유저를 찾을 수 없습니다."));

        // 등록할 가게 엔티티 생성
        Store newStore = Store.createStore(
                requestDto.getName(),
                requestDto.getCategory(),
                requestDto.getTel(),
                requestDto.getOpenTime(),
                requestDto.getCloseTime(),
                requestDto.getMinPrice(),
                requestDto.getAddress(),
                requestDto.getRating(),
                user,
                requestDto.getStoreOperationStatus()
        );

        newStore.updateOperationStatus();  // 현재 상태(개점/마감) 업데이트
        Store savedStore = storeRepository.save(newStore); // 가게 등록

        return StoreCreateResponseDto.of(savedStore);

    }


}
