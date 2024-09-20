package org.sparta.outsourcingproject.domain.menu.service;

import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.domain.menu.dto.MenuEditRequestDto;
import org.sparta.outsourcingproject.domain.menu.dto.MenuEditResponseDto;
import org.sparta.outsourcingproject.domain.menu.dto.MenuRequestDto;
import org.sparta.outsourcingproject.domain.menu.dto.MenuResponseDto;
import org.sparta.outsourcingproject.domain.menu.entity.Menu;
import org.sparta.outsourcingproject.domain.menu.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public MenuResponseDto createMenu(MenuRequestDto menuRequestDto) {
        // 메뉴 생성은 사장님만 가능.  - JWT를 가져와 인증하는 절차.

        // 전달받은 메뉴 이름(field)이 존재하는지 판별, unique 하기때문.

        // 메뉴 생성
        Menu menu = new Menu(menuRequestDto.getField(), menuRequestDto.getType(), menuRequestDto.getPrice());

        // 메뉴 저장
        Menu savedMenu = menuRepository.save(menu);

        return new MenuResponseDto(savedMenu);
    }

    @Transactional
    public MenuEditResponseDto updateMenu(MenuEditRequestDto menuEditRequestDto, Long menuId) {
        // 메뉴 수정은 사장님만 가능.  - JWT를 가져와 인증하는 절차.

        // 전달받은 메뉴 Id로 검색.
        Menu updatedMenu = menuRepository.getOne(menuId);
        // 메뉴 내용을 변경 - dirty checking으로 자동 반영.


        //임시 코드
        return new MenuEditResponseDto(updatedMenu);
    }
}
