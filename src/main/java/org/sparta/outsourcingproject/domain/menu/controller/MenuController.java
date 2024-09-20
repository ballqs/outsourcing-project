package org.sparta.outsourcingproject.domain.menu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.domain.menu.dto.MenuEditRequestDto;
import org.sparta.outsourcingproject.domain.menu.dto.MenuEditResponseDto;
import org.sparta.outsourcingproject.domain.menu.dto.MenuRequestDto;
import org.sparta.outsourcingproject.domain.menu.dto.MenuResponseDto;
import org.sparta.outsourcingproject.domain.menu.service.MenuService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;


    @PostMapping("/register")
    public ResponseEntity<MenuResponseDto> createMenu(@Valid @RequestBody MenuRequestDto menuRequestDto) {
        MenuResponseDto menuResponseDto = menuService.createMenu(menuRequestDto);
        return new ResponseEntity<>(menuResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuEditResponseDto> updateMenu(@Valid @RequestBody MenuEditRequestDto menuEditRequestDto, @PathVariable Long menuId) {
        MenuEditResponseDto menuEditResponseDto = menuService.updateMenu(menuEditRequestDto, menuId);
        return new ResponseEntity<>(menuEditResponseDto, HttpStatus.OK);
    }
}
