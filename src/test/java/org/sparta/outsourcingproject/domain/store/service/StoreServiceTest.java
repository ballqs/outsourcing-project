package org.sparta.outsourcingproject.domain.store.service;

import org.apache.catalina.mbeans.UserMBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sparta.outsourcingproject.domain.menu.entity.Menu;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreCreateRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreListRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreUpdateRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreCreateResponseDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreGetResponseDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreResponseDto;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.store.enums.StoreOperationStatus;
import org.sparta.outsourcingproject.domain.store.enums.StoreStatus;
import org.sparta.outsourcingproject.domain.store.repository.StoreRepository;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSaveRequestDto;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.sparta.outsourcingproject.domain.user.enums.UserRole;
import org.sparta.outsourcingproject.domain.user.service.UserService;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;


    @Test
    void 가게_등록에_성공한다() {
        // given
        long userId = 1L;

        List<Store> stores = new ArrayList<>();

        User user = new User();
        ReflectionTestUtils.setField(user, "id", userId);
        ReflectionTestUtils.setField(user, "authority", UserRole.OWNER);

        StoreCreateRequestDto requestDto = new StoreCreateRequestDto(
                "Test",
                "pizza",
                "123-4567",
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                10000,
                "city",
                StoreOperationStatus.OPERATE,
                BigDecimal.valueOf(0.0)
        );

        given(storeRepository.findByUserId(userId)).willReturn(stores); // 해당 유저의 가게 수 체크(0개)
        given(userService.findUser(userId)).willReturn(user); // 유저 존재 확인 및 권한 통과

        Store store = Store.createStore(requestDto, user);
        given(storeRepository.save(any(Store.class))).willReturn(store);


        // when
        StoreCreateResponseDto result = storeService.createStore(requestDto, user.getId());


        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(store.getName());
        assertThat(result.getCategory()).isEqualTo(store.getCategory());
        assertThat(result.getMinPrice()).isEqualTo(store.getMinPrice());

        verify(storeRepository, times(1)).save(any(Store.class));

    }

    @Test
    void 가게_단건_조회에_성공한다() {
        // given
        long userId = 1L;
        long storeId = 1L;

        User user = new User();
        ReflectionTestUtils.setField(user, "id", userId);

        StoreCreateRequestDto requestDto = new StoreCreateRequestDto(
                "Test",
                "pizza",
                "123-4567",
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                10000,
                "city",
                StoreOperationStatus.OPERATE,
                BigDecimal.valueOf(0.0)
        );

        Store store = Store.createStore(requestDto, user);
        ReflectionTestUtils.setField(store, "id", storeId);
        ReflectionTestUtils.setField(store, "status", StoreStatus.OPENED);

        Menu menu1 = new Menu("포테이토 피자", "양식", 20000);
        Menu menu2 = new Menu("콤비네이션 피자", "양식", 18000);
        List<Menu> menus = List.of(menu1, menu2);
        ReflectionTestUtils.setField(store, "menus", menus);

        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));

        // when
        StoreGetResponseDto result = storeService.getStore(storeId);

        // then
        assertThat(result).isNotNull();

        assertThat(result.getName()).isEqualTo(store.getName());
        assertThat(result.getAddress()).isEqualTo(store.getAddress());
        assertThat(result.getTel()).isEqualTo(store.getTel());

        assertThat(result.getMenus().get(0).getName()).isEqualTo(menu1.getName());
        assertThat(result.getMenus().get(1).getName()).isEqualTo(menu2.getName());
    }
    
    @Test
    void 가게_다건_조회에_성공한다() {
        // given
        long userId = 1L;
        User user = new User();
        ReflectionTestUtils.setField(user, "id", userId);

        StoreListRequestDto requestDto = new StoreListRequestDto("Test");

        StoreCreateRequestDto requestDto1 = new StoreCreateRequestDto(
                "Test1",
                "pizza",
                "123-4567",
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                10000,
                "city1",
                StoreOperationStatus.OPERATE,
                BigDecimal.valueOf(0.0)
        );

        StoreCreateRequestDto requestDto2 = new StoreCreateRequestDto(
                "Test2",
                "chicken",
                "123-4357",
                LocalTime.of(8, 0),
                LocalTime.of(23, 0),
                20000,
                "city2",
                StoreOperationStatus.OPERATE,
                BigDecimal.valueOf(0.0)
        );

        StoreCreateRequestDto requestDto3 = new StoreCreateRequestDto(
                "Test3",
                "hamburger",
                "123-5893",
                LocalTime.of(12, 0),
                LocalTime.of(21, 0),
                15000,
                "city3",
                StoreOperationStatus.SHUTDOWN,
                BigDecimal.valueOf(0.0)
        );

        Store store1 = Store.createStore(requestDto1, user);
        Store store2 = Store.createStore(requestDto2, user);
        Store store3 = Store.createStore(requestDto3, user); // 폐업한 가게여서 조회되면 X

        given(storeRepository.findAll()).willReturn(List.of(store1, store2, store3));

        // when
        List<StoreResponseDto> result = storeService.getStores(requestDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2); // 폐업한 마지막 가게는 제외되는 게 맞음
        assertThat(result.get(0).getName()).isEqualTo(store1.getName());
        assertThat(result.get(1).getName()).isEqualTo(store2.getName());
    }

    @Test
    void 가게_정보_수정에_성공한다() {
        // given
        long storeId = 1L;
        long userId = 1L;
        User user = new User();
        ReflectionTestUtils.setField(user, "id", userId);

        StoreCreateRequestDto createRequestDto = new StoreCreateRequestDto(
                "Test",
                "pizza",
                "123-4567",
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                10000,
                "city",
                StoreOperationStatus.OPERATE,
                BigDecimal.valueOf(0.0)
        );

        Store store = Store.createStore(createRequestDto, user);
        ReflectionTestUtils.setField(store, "id", storeId);

        StoreUpdateRequestDto updateRequestDto = new StoreUpdateRequestDto();
        ReflectionTestUtils.setField(updateRequestDto, "name", "updateName");
        ReflectionTestUtils.setField(updateRequestDto, "category", "updateCategory");

        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));

        // when
        StoreGetResponseDto result = storeService.updateStoreInfo(updateRequestDto, storeId);

        // then
        assertThat(result.getName()).isEqualTo(updateRequestDto.getName());
        assertThat(result.getCategory()).isEqualTo(updateRequestDto.getCategory());
    }
    
    @Test
    void 가게를_폐업_상태로_전환하는데_성공한다() {
        // given
        long storeId = 1L;
        long userId = 1L;
        User user = new User();
        ReflectionTestUtils.setField(user, "id", userId);

        StoreCreateRequestDto createRequestDto = new StoreCreateRequestDto(
                "Test",
                "pizza",
                "123-4567",
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                10000,
                "city",
                StoreOperationStatus.OPERATE,
                BigDecimal.valueOf(0.0)
        );

        Store store = Store.createStore(createRequestDto, user);
        ReflectionTestUtils.setField(store, "id", storeId);

        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));

        // when
        storeService.setShutdownStore(store.getId());
        
        
        // then
        assertThat(store.getOperationStatus()).isEqualTo(StoreOperationStatus.SHUTDOWN);
    }
}

