package org.sparta.outsourcingproject.domain.store.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.dto.ResponseDto;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreCreateRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreUpdateRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreCreateResponseDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreGetResponseDto;
import org.sparta.outsourcingproject.domain.store.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    /**
     * 가게 등록 API
     */
    @PostMapping
    public ResponseDto<StoreCreateResponseDto> createStore(@RequestBody StoreCreateRequestDto requestDto, HttpServletRequest httpServletRequest) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");
        StoreCreateResponseDto res = storeService.createStore(requestDto, userId);
        return new ResponseDto<>(HttpStatus.CREATED.value(), res, "가게 등록에 성공하였습니다!");
    }

    /**
     * 가게 단건 조회 API
     */
    @GetMapping("/{storeId}")
    public ResponseDto<StoreGetResponseDto> getStore(@PathVariable Long id) {
        StoreGetResponseDto res = storeService.getStore(id);
        return new ResponseDto<>(HttpStatus.CREATED.value(), res, "가게 단건 조회에 성공하였습니다!");
    }


    /**
     * 가게 정보 수정 API
     */
    public ResponseDto<StoreGetResponseDto> updateStoreInfo(@RequestBody StoreUpdateRequestDto requestDto, @PathVariable Long id) {
        StoreGetResponseDto res = storeService.updateStoreInfo(requestDto, id);
        return new ResponseDto<>(HttpStatus.OK.value(), res, "가게 정보가 성공적으로 수정되었습니다.");
    }

    /**
     * 가게 폐업 처리 API
     */
    @PatchMapping("/shutdown/{storeId}")
    public ResponseDto shutdownStore(@PathVariable Long id) {
        storeService.setShutdownStore(id);
        return new ResponseDto<>(HttpStatus.OK.value(), null, "해당 가게가 폐업 상태로 전환되었습니다");
    }





}
