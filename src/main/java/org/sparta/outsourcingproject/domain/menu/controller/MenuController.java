package org.sparta.outsourcingproject.domain.menu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.common.annotation.Auth;
import org.sparta.outsourcingproject.common.dto.AuthUser;
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
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/register/stores/{storeId}")
    public ResponseEntity<MenuResponseDto> createMenu(@Auth AuthUser authUser, @Valid @RequestBody MenuRequestDto menuRequestDto, @PathVariable Long storeId) {
        MenuResponseDto menuResponseDto = menuService.createMenu(authUser.getUserId(), menuRequestDto, storeId);
        return new ResponseEntity<>(menuResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuEditResponseDto> updateMenu(@Auth AuthUser authUser, @Valid @RequestBody MenuEditRequestDto menuEditRequestDto, @PathVariable Long menuId) {
        MenuEditResponseDto menuEditResponseDto = menuService.updateMenu(authUser.getUserId(), menuEditRequestDto, menuId);
        return new ResponseEntity<>(menuEditResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{menuId}/stores/{storeId}")
    public ResponseEntity<Void> deleteMenu(@Auth AuthUser authUser, @PathVariable Long menuId, @PathVariable Long storeId) {
        menuService.deleteMenu(authUser.getUserId(), menuId, storeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
