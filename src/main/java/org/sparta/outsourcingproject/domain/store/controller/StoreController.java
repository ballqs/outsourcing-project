package org.sparta.outsourcingproject.domain.store.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.annotation.Permission;
import org.sparta.outsourcingproject.common.dto.ResponseDto;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreCreateRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreListRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreUpdateRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreCreateResponseDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreGetResponseDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreResponseDto;
import org.sparta.outsourcingproject.domain.store.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    /**
     * 가게 등록 API
     */

    @Permission
    @PostMapping
    public ResponseEntity<ResponseDto<StoreCreateResponseDto>> createStore(@RequestBody StoreCreateRequestDto requestDto, HttpServletRequest httpServletRequest) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");
        StoreCreateResponseDto res = storeService.createStore(requestDto, userId);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value() , res , "가게 등록에 성공하였습니다!"));
    }

    /**
     * 가게 단건 조회 API(가게 메뉴 포함)
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<ResponseDto<StoreGetResponseDto>> getStore(@PathVariable Long storeId) {
        StoreGetResponseDto res = storeService.getStore(storeId);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value() , res , "가게 단건 조회에 성공하였습니다!"));
    }

    /**
     * 가게 다건 조회 API(가게 메뉴 포함 X)
     */
    @GetMapping
    public ResponseEntity<ResponseDto<List<StoreResponseDto>>> getStores(@RequestBody StoreListRequestDto requestDto) {
        List<StoreResponseDto> res = storeService.getStores(requestDto);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value() , res , "해당 이름을 포함하는 모든 가게 조회에 성공하였습니다."));
    }

    /**
     * 가게 정보 수정 API
     */

    @Permission
    @PatchMapping("/{storeId}")
    public ResponseEntity<ResponseDto<StoreGetResponseDto>> updateStoreInfo(@RequestBody StoreUpdateRequestDto requestDto, @PathVariable Long storeId) {
        StoreGetResponseDto res = storeService.updateStoreInfo(requestDto, storeId);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value() , res , "가게 정보가 성공적으로 수정되었습니다."));
    }

    /**
     * 가게 폐업 처리 API
     */

    @Permission
    @PatchMapping("/shutdown/{storeId}")
    public ResponseEntity<ResponseDto<String>> shutdownStore(@PathVariable Long storeId) {
        storeService.setShutdownStore(storeId);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value() , null , "해당 가게가 폐업 상태로 전환되었습니다"));
    }

}
