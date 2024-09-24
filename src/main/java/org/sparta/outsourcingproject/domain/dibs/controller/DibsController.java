package org.sparta.outsourcingproject.domain.dibs.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.dto.ResponseDto;
import org.sparta.outsourcingproject.domain.dibs.dto.DibsResponseDto;
import org.sparta.outsourcingproject.domain.dibs.service.DibsService;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/dibs")
public class DibsController {

    private final DibsService dibsService;

    /**
     * 찜 추가
     */
    @PostMapping("/{storeId}")
    public ResponseEntity<ResponseDto<DibsResponseDto>> callDibs(@PathVariable Long storeId, HttpServletRequest httpServletRequest) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");
        DibsResponseDto res = dibsService.callDibs(storeId, userId);
        log.info("ID {}인 유저가 ID {}인 가게를 찜하였습니다.", userId, storeId);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.CREATED.value(), res, "해당 가게를 찜하였습니다."));
    }

    /**
     * 찜 삭제
     */
    @DeleteMapping("/{storeId}")
    public ResponseEntity<ResponseDto<String>> cancelDibs(@PathVariable Long storeId, HttpServletRequest httpServletRequest) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), null, dibsService.deleteDibs(storeId, userId)));
    }

    /**
     * 찜 목록 조회
     */
    @GetMapping
    public ResponseEntity<ResponseDto<List<StoreResponseDto>>> getDibs(HttpServletRequest httpServletRequest) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");
        List<StoreResponseDto> res = dibsService.getDips(userId);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), res, "찜한 가게 목록 조회에 성공하였습니다."));
    }
}
