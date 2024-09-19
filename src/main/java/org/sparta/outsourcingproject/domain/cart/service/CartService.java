package org.sparta.outsourcingproject.domain.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.outsourcingproject.domain.cart.dto.CartDetailInsertDto;
import org.sparta.outsourcingproject.domain.cart.dto.CartDetailUpdateDto;
import org.sparta.outsourcingproject.domain.cart.dto.CartInsertDto;
import org.sparta.outsourcingproject.domain.cart.dto.CartRequestInsertDto;
import org.sparta.outsourcingproject.domain.cart.entity.Cart;
import org.sparta.outsourcingproject.domain.cart.entity.CartDetail;
import org.sparta.outsourcingproject.domain.cart.repository.CartRepository;
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

    @Transactional
    public void createCart(CartRequestInsertDto cartRequestInsertDto) {
        CartInsertDto cartInsertDto = cartRequestInsertDto.getCart();
        CartDetailInsertDto cartDetailInsertDto = cartRequestInsertDto.getCartDetail();

        Long menuId = cartDetailInsertDto.getMenuId();
        String menuName = cartDetailInsertDto.getMenuName();
        int menuPrice = cartDetailInsertDto.getMenuPrice();
        int cnt = cartDetailInsertDto.getCnt();

        int sum = menuPrice * cnt;

        // ★★★★★★★User user 체크한 값이다.★★★★★★★★
        Long userId = cartInsertDto.getUserId();
        // User user = userService.findById();
        // ★★★★★★★Store Store 가져오는 곳.★★★★★★★★
        // Store store = StoreService.findById();

        Optional<Cart> getCartInfo = cartRepository.findByUserId(userId);
        Cart cart;

        // 저장된 store값과 현재 들고온 store값이 다르면 장바구니 초기화(추후 작업)
        if (getCartInfo.isPresent() && 1 == 2) {
            cart = getCartInfo.get();
            int nowTotalAmt = cartDetailService.getSumAmt(cart.getId()) + sum;
            cart.updateTotalAmt(nowTotalAmt);
        } else {
            cartRepository.deleteByUserId(userId);
            cart = new Cart(cartInsertDto.getStoreId() , cartInsertDto.getUserId() , sum);
        }

        Cart saveCart = cartRepository.save(cart);

        cartDetailService.saveCartDetail(new CartDetail(saveCart , menuId , menuName , menuPrice , cnt));
    }

    // @transactionaleventlistener 공부해서 적용하기

    @Transactional
    public void updateCart(Long cartDetailId , CartDetailUpdateDto cartDetailUpdateDto) {
        CartDetail cartDetail = cartDetailService.getCartDetail(cartDetailId);
        Cart cart = cartDetail.getCart();

        Long menuId = cartDetailUpdateDto.getMenuId();
        String menuName = cartDetailUpdateDto.getMenuName();
        int menuPrice = cartDetailUpdateDto.getMenuPrice();
        int cnt = cartDetailUpdateDto.getCnt();

        // 1.cart 테이블의 금액 변경
        cart.updateTotalAmt(cart.getTotalAmt() - (cartDetail.getMenuPrice() * cartDetail.getCnt()) + (menuPrice * cnt));

        // 2.cartDetail 테이블 update
        cartDetail.update(menuId , menuName , menuPrice , cnt);
        cartDetailService.saveCartDetail(cartDetail);
    }

    @Transactional
    public void deleteCart(Long cartDetailId) {
        CartDetail cartDetail = cartDetailService.getCartDetail(cartDetailId);
        cartDetailService.deleteCartDetail(cartDetailId);
        List<CartDetail> cartDetails = cartDetailService.getAllCartDetails(cartDetail.getCart().getId());
        if (cartDetails.size() == 0) {
            cartRepository.deleteById(cartDetail.getCart().getId());
        }
    }

}
