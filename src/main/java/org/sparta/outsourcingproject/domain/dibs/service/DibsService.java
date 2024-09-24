package org.sparta.outsourcingproject.domain.dibs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.domain.cart.exception.NotFoundDibs;
import org.sparta.outsourcingproject.domain.dibs.dto.DibsResponseDto;
import org.sparta.outsourcingproject.domain.dibs.entity.Dibs;
import org.sparta.outsourcingproject.domain.dibs.repository.DibsRepository;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreResponseDto;
import org.sparta.outsourcingproject.domain.store.exception.StoreNotFoundException;
import org.sparta.outsourcingproject.domain.store.repository.StoreRepository;
import org.sparta.outsourcingproject.domain.store.service.StoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DibsService {

    private final DibsRepository dibsRepository;
    private final StoreService storeService;
    private final StoreRepository storeRepository;

    // 찜하기(call dibs)
    @Transactional
    public DibsResponseDto callDibs(Long storeId, Long userId) {

        storeService.findStore(storeId); // 찜하려는 가게가 존재하는지, 폐업 상태인지 확인

        Dibs dibs = Dibs.CreateDibs(storeId, userId); // 찜 생성

        Dibs savedDibs = dibsRepository.save(dibs);
        return DibsResponseDto.of(savedDibs);
    }

    //본인 찜 삭제
    @Transactional
    public String deleteDibs(Long storeId, Long userId) {
        dibsRepository.deleteByIdStoreIdAndIdUserId(storeId, userId)
                .orElseThrow(() -> new NotFoundDibs(ErrorCode.DIBS_NOT_FOUND));

        return "해당 가게를 찜 목록에서 삭제하였습니다.";
    }

    // 내 찜 가게 목록 조회
    public List<StoreResponseDto> getDips(Long userId) {
        return dibsRepository.findByIdUserId(userId).stream()
                .map(dibs -> dibs.getId().getStoreId())  // storeId 추출
                .map(storeId -> storeRepository.findById(storeId)  // storeId로 가게 조회
                        .orElseThrow(() -> new StoreNotFoundException(ErrorCode.STORE_NOT_FOUND)))
                .map(StoreResponseDto::of)  // Store 엔티티를 StoreResponseDto로 변환
                .collect(Collectors.toList());
    }

}
