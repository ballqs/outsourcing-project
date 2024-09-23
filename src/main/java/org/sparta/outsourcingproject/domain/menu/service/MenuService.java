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
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.store.service.StoreService;
import org.sparta.outsourcingproject.domain.user.Authority;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.sparta.outsourcingproject.domain.user.repository.UserRepository;
import org.sparta.outsourcingproject.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final StoreService storeService;

    @Transactional
    public MenuResponseDto createMenu(Long userId, MenuRequestDto menuRequestDto, Long storeId) {

        Store store = storeService.findStore(storeId);

        // 전달받은 메뉴 이름(unique name)이 이미 존재하는가.
        if (menuRepository.existsByName(menuRequestDto.getName())) {
            throw new MenuAlreadyExistsException(ErrorCode.MENU_ALREADYEXISTS_ERROR);
        }

        // 메뉴 생성
        Menu menu = new Menu(menuRequestDto, store);

        // 메뉴 저장
        Menu savedMenu = menuRepository.save(menu);

        return new MenuResponseDto(savedMenu);
    }

    @Transactional
    public MenuEditResponseDto updateMenu(Long userId, MenuEditRequestDto menuEditRequestDto, Long storeId, Long menuId) {
        // 메뉴 수정은 사장님만 가능.
        User user = userService.findUser(userId);

        // 수정할 메뉴 존재 유무 확인.
        if (!menuRepository.existsById(menuId)) {
            throw new MenuNotExistsException(ErrorCode.MENU_NOTEXISTS_ERROR);
        }

        // 메뉴 내용을 변경 - dirty checking으로 자동 반영.
        Menu updatedMenu = getMenu(storeId , menuId);

        updatedMenu.update(menuEditRequestDto);

        //임시 코드
        return new MenuEditResponseDto(updatedMenu);
    }

    @Transactional
    public void deleteMenu(Long userId, Long menuId, Long storeId) {
        // 본인 가게의 메뉴만 삭제가 가능.
        // Store정보 -> userId가 일치하지 않거나, 사장이 아니라면 권한 X
        Store store = storeService.findStore(storeId);
        if (!store.getUser().getId().equals(userId)) {
            throw new AuthorityMismatchException(ErrorCode.AUTHORITY_MISMATCH_ERROR);
        }

        // 권한 OK -> 가게의 메뉴목록
        List<Menu> menus = store.getMenus();

        // 메뉴 삭제
        boolean menuDeleted = menus.stream()
                .filter(menu -> menu.getId().equals(menuId))
                .peek(Menu::delete)
                .findFirst()
                .isPresent();

        if (!menuDeleted) {
            throw new MenuNotExistsException(ErrorCode.MENU_NOTEXISTS_ERROR);
        }
    }

    // store 작성자와 논의 필요부분.
    public List<Menu> getFilteredMenus(Long storeId) {
        // 메뉴는 단독 조회 불가능, 가게 조회 시 함께 조회.
        // 가게 메뉴 조회시 삭제된 메뉴는 조회 x
        Store store = storeService.findStore(storeId);

        List<Menu> menus = new ArrayList<>();

        store.getMenus().stream()
                .filter(menu -> !menu.isStatus()) // status가 false인 메뉴 필터링
                .forEach(menus::add); // 필터링된 메뉴를 새로운 리스트에 추가

        return menus;
    }

    // 주문 내역 조회시에는 삭제된 메뉴의 정보도 포함.
    public List<Menu> getAllMenus(Long storeId) {
        Store store = storeService.findStore(storeId);

        return store.getMenus();
    }

    public Menu getMenu(Long storeId , Long menuId) {
        // 유저측에서는 status가 1일때 삭제되지 않은 계정 0일때 삭제된 계정
        // 해당 메뉴가 품절되었는지? 아님 더이상 판매 안하는 삭제된 메뉴인지?
        return menuRepository.findByIdAndStoreId(menuId , storeId).orElseThrow(() -> new MenuNotExistsException(ErrorCode.MENU_NOTEXISTS_ERROR));
    }
}
