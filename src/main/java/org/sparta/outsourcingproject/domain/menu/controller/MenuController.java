package org.sparta.outsourcingproject.domain.menu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.common.annotation.Auth;
import org.sparta.outsourcingproject.common.annotation.Permission;
import org.sparta.outsourcingproject.common.dto.AuthUser;
import org.sparta.outsourcingproject.common.dto.ResponseDto;
import org.sparta.outsourcingproject.domain.menu.dto.request.MenuEditRequestDto;
import org.sparta.outsourcingproject.domain.menu.dto.response.MenuEditResponseDto;
import org.sparta.outsourcingproject.domain.menu.dto.request.MenuRequestDto;
import org.sparta.outsourcingproject.domain.menu.dto.response.MenuResponseDto;
import org.sparta.outsourcingproject.domain.menu.service.MenuService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/stores")
public class MenuController {

    private final MenuService menuService;

    @Permission
    @PostMapping("/{storeId}/menus")
    public ResponseEntity<ResponseDto<MenuResponseDto>> createMenu(@Auth AuthUser authUser, @Valid @RequestBody MenuRequestDto menuRequestDto, @PathVariable Long storeId) {
        MenuResponseDto menuResponseDto = menuService.createMenu(authUser.getUserId(), menuRequestDto, storeId);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value() , menuResponseDto , "메뉴가 등록되었습니다."));
    }

    @Permission
    @PatchMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseDto<MenuEditResponseDto>> updateMenu(
            @Auth AuthUser authUser,
            @Valid @RequestBody MenuEditRequestDto menuEditRequestDto,
            @PathVariable Long storeId,
            @PathVariable Long menuId) {
        MenuEditResponseDto menuEditResponseDto = menuService.updateMenu(authUser.getUserId(), menuEditRequestDto, storeId, menuId);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value() , menuEditResponseDto , "메뉴가 수정되었습니다."));
    }

    @DeleteMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseDto<String>> deleteMenu(
            @Auth AuthUser authUser,
            @PathVariable Long storeId,
            @PathVariable Long menuId) {
        menuService.deleteMenu(authUser.getUserId(), menuId, storeId);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value() , null , "메뉴가 삭제되었습니다."));
    }
}
