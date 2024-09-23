package org.sparta.outsourcingproject.domain.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.common.code.ErrorCode;
import org.sparta.outsourcingproject.common.exception.custom.NotFoundException;
import org.sparta.outsourcingproject.domain.cart.dto.*;
import org.sparta.outsourcingproject.domain.cart.entity.Cart;
import org.sparta.outsourcingproject.domain.cart.entity.CartDetail;
import org.sparta.outsourcingproject.domain.cart.exception.CartUnauthorizedException;
import org.sparta.outsourcingproject.domain.cart.exception.MinimumOrderAmountException;
import org.sparta.outsourcingproject.domain.cart.java.CartDetailEvent;
import org.sparta.outsourcingproject.domain.cart.repository.CartRepository;
import org.sparta.outsourcingproject.domain.menu.entity.Menu;
import org.sparta.outsourcingproject.domain.menu.service.MenuService;
import org.sparta.outsourcingproject.domain.order.service.OrdersService;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.store.service.StoreService;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.sparta.outsourcingproject.domain.user.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j(topic = "CartService")
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartDetailService cartDetailService;
    private final OrdersService ordersService;
    private final ApplicationEventPublisher eventPublisher;
    private final UserService userService;
    private final StoreService storeService;
    private final MenuService menuService;

    @Transactional
    public void createCart(Long userId , CartRequestInsertDto cartRequestInsertDto) {
        CartInsertDto cartInsertDto = cartRequestInsertDto.getCart();
        CartDetailInsertDto cartDetailInsertDto = cartRequestInsertDto.getCartDetail();

        Long menuId = cartDetailInsertDto.getMenuId();
        int cnt = cartDetailInsertDto.getCnt();


        User user = userService.findUser(userId);
        Store store = storeService.findStore(cartInsertDto.getStoreId());
        Menu menu = menuService.getMenu(menuId);

        int sum = menu.getPrice() * cnt;

        Optional<Cart> getCartInfo = cartRepository.findByUserId(user.getId());
        Cart cart;
        if (getCartInfo.isPresent() && getCartInfo.get().getStore().getId().equals(store.getId())) {
            cart = getCartInfo.get();
            int nowTotalAmt = cartDetailService.getSumAmt(cart.getId()) + sum;
            cart.updateTotalAmt(nowTotalAmt);
        } else {
            cartRepository.deleteByUserId(userId);
            cart = new Cart(user , store , sum);
        }

        Cart saveCart = cartRepository.save(cart);

        CartDetail cartDetail = new CartDetail(saveCart , menu , menu.getName() , menu.getPrice() , cnt);
        eventPublisher.publishEvent(new CartDetailEvent(cartDetail));
    }

    // @transactionaleventlistener 공부해서 적용하기

    @Transactional
    public void updateCart(Long userId , Long cartDetailId , CartDetailUpdateDto cartDetailUpdateDto) {
        CartDetail cartDetail = cartDetailService.getCartDetail(cartDetailId);
        Cart cart = cartDetail.getCart();
        if (!userId.equals(cart.getUser().getId())) {
            throw new CartUnauthorizedException(ErrorCode.FORBIDDEN_CART_MODIFICATION);
        }

        Long menuId = cartDetailUpdateDto.getMenuId();
        int cnt = cartDetailUpdateDto.getCnt();

        Menu menu = menuService.getMenu(menuId);

        // 1.cart 테이블의 금액 변경
        cart.updateTotalAmt(cart.getTotalAmt() - (cartDetail.getMenuPrice() * cartDetail.getCnt()) + (menu.getPrice() * cnt));

        // 2.cartDetail 테이블 update
        cartDetail.update(menu , menu.getName() , menu.getPrice() , cnt);
        eventPublisher.publishEvent(new CartDetailEvent(cartDetail));
    }

    @Transactional
    public void deleteCart(Long userId , Long cartDetailId) {
        CartDetail cartDetail = cartDetailService.getCartDetail(cartDetailId);

        if (!userId.equals(cartDetail.getCart().getUser().getId())) {
            throw new CartUnauthorizedException(ErrorCode.FORBIDDEN_CART_DELETION);
        }

        cartDetailService.deleteCartDetail(cartDetailId);
        List<CartDetail> cartDetails = cartDetailService.getAllCartDetails(cartDetail.getCart().getId());
        if (cartDetails.size() == 0) {
            cartRepository.deleteById(cartDetail.getCart().getId());
        }
    }

    @Transactional
    public void orderComplete(Long userId) {
        Optional<Cart> opCart = cartRepository.findByUserId(userId);

        if (opCart.isEmpty()) {
            throw new NotFoundException(ErrorCode.CART_NOT_FOUND);
        }
        Cart cart = opCart.get();

        // 장바구니에 제대로 들어있는지?
        int cartSize = cartRepository.countByUserId(userId);
        int cartDetailSize = cartDetailService.countCartDetail(cart.getId());
        if (cartSize < 1 || cartDetailSize < 1) {
            throw new NotFoundException(ErrorCode.CART_NOT_FOUND);
        }

        // 최소 주문금액을 넘겼는지?
        Store store = storeService.findStore(cart.getStore().getId());
        if (cart.getTotalAmt() < store.getMinPrice()) {
            throw new MinimumOrderAmountException(ErrorCode.BAD_REQUEST_MINIMUM_ORDER_NOT_MET);
        }

        // 주문 완료 작업
        ordersService.orderComplete(cart);
        cartRepository.delete(cart);
    }

    public CartResponseSelectDto getCarts(Long userId) {
        Optional<Cart> opCart = cartRepository.findByUserId(userId);
        Cart cart = opCart.get();
        CartSelectDto cartSelectDto = new CartSelectDto(cart);
        List<CartDetail> cartDetails = cartDetailService.getAllCartDetails(cart.getId());
        List<CartDetailSelectDto> cartDetailSelectDtos = cartDetails.stream().map(CartDetailSelectDto::new).toList();
        return new CartResponseSelectDto(cartSelectDto , cartDetailSelectDtos);
    }

}
