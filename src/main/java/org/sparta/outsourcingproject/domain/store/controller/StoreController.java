package org.sparta.outsourcingproject.domain.store.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.dto.ResponseDto;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreCreateRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreResponseDto;
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
    public ResponseDto<StoreResponseDto> createStore(@RequestBody StoreCreateRequestDto requestDto, HttpServletRequest httpServletRequest) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");
        StoreResponseDto res = storeService.createStore(requestDto, userId);
        return new ResponseDto<>(HttpStatus.CREATED.value(), res, "가게 등록에 성공하였습니다!");
    }

    /**
     * 가게 단건 조회 API
     */
    @GetMapping("/{storeId}")
    public ResponseDto<StoreResponseDto> getStore(@RequestParam Long id) {
        StoreResponseDto res = storeService.getStore(id);
        return new ResponseDto<>(HttpStatus.CREATED.value(), res, "가게 단건 조회에 성공하였습니다!");
    }


}
