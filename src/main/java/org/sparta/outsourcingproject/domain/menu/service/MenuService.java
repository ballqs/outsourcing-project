package org.sparta.outsourcingproject.domain.menu.service;

import lombok.RequiredArgsConstructor;
import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.domain.menu.dto.request.MenuEditRequestDto;
import org.sparta.outsourcingproject.domain.menu.dto.response.MenuEditResponseDto;
import org.sparta.outsourcingproject.domain.menu.dto.request.MenuRequestDto;
import org.sparta.outsourcingproject.domain.menu.dto.response.MenuResponseDto;
import org.sparta.outsourcingproject.domain.menu.entity.Menu;
import org.sparta.outsourcingproject.domain.menu.exception.AuthorityMismatchException;
import org.sparta.outsourcingproject.domain.menu.exception.MenuAlreadyExistsException;
import org.sparta.outsourcingproject.domain.menu.exception.MenuNotExistsException;
import org.sparta.outsourcingproject.domain.menu.repository.MenuRepository;
import org.sparta.outsourcingproject.domain.user.Authority;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.sparta.outsourcingproject.domain.user.repository.UserRepository;
import org.sparta.outsourcingproject.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public MenuResponseDto createMenu(Long userId, MenuRequestDto menuRequestDto) {

        // 메뉴 생성은 사장님만 가능.
        User user = userService.findUser(userId);
        if (!user.getAuthority().equals(Authority.OWNER)) {
            throw new AuthorityMismatchException(ErrorCode.AUTHORITY_MISMATCH_ERROR);
        }

        // 전달받은 메뉴 이름(unique name)이 이미 존재하는가.
        if (menuRepository.existsByName(menuRequestDto.getName())) {
            throw new MenuAlreadyExistsException(ErrorCode.MENU_ALREADYEXISTS_ERROR);
        }

        // 메뉴 생성
        Menu menu = new Menu(menuRequestDto.getName(), menuRequestDto.getType(), menuRequestDto.getPrice());

        // 메뉴 저장
        Menu savedMenu = menuRepository.save(menu);

        return new MenuResponseDto(savedMenu);
    }

    @Transactional
    public MenuEditResponseDto updateMenu(Long userId, MenuEditRequestDto menuEditRequestDto, Long menuId) {
        // 메뉴 수정은 사장님만 가능.
        User user = userService.findUser(userId);
        if (!user.getAuthority().equals(Authority.OWNER)) {
            throw new AuthorityMismatchException(ErrorCode.AUTHORITY_MISMATCH_ERROR);
        }

        // 수정할 메뉴 존재 유무 확인.
        if (!menuRepository.existsById(menuId)) {
            throw new MenuNotExistsException(ErrorCode.MENU_NOTEXISTS_ERROR);
        }

        // 메뉴 내용을 변경 - dirty checking으로 자동 반영.
        Menu updatedMenu = getMenu(menuId);

        updatedMenu.update(menuEditRequestDto);

        //임시 코드
        return new MenuEditResponseDto(updatedMenu);
    }

    public Menu getMenu(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(() -> new MenuNotExistsException(ErrorCode.MENU_NOTEXISTS_ERROR));
    }
}
