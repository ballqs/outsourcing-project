package org.sparta.outsourcingproject.domain.dibs.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sparta.outsourcingproject.domain.dibs.dto.DibsResponseDto;
import org.sparta.outsourcingproject.domain.dibs.entity.Dibs;
import org.sparta.outsourcingproject.domain.dibs.repository.DibsRepository;
import org.sparta.outsourcingproject.domain.store.dto.request.StoreCreateRequestDto;
import org.sparta.outsourcingproject.domain.store.dto.response.StoreResponseDto;
import org.sparta.outsourcingproject.domain.store.entity.Store;
import org.sparta.outsourcingproject.domain.store.enums.StoreOperationStatus;
import org.sparta.outsourcingproject.domain.store.repository.StoreRepository;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DibsServiceTest {

    @Mock
    private DibsRepository dibsRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private DibsService dibsService;

    @Test
    void 가게를_나의_찜_목록에_추가하는데_성공한다() {
        // given
        long userId = 1L;
        long storeId = 1L;

        Dibs dibs = Dibs.CreateDibs(storeId, userId);

        when(dibsRepository.save(any(Dibs.class))).thenReturn(dibs);
//        given(dibsRepository.save(any(Dibs.class))).willReturn(dibs);

        // when
        DibsResponseDto result = dibsService.callDibs(storeId, userId);


        // then
        assertThat(result).isNotNull();
        assertThat(result.getStoreId()).isEqualTo(storeId);
        assertThat(result.getUserId()).isEqualTo(userId);
    }

    @Test
    void 가게를_내_찜_목록에서_삭제하는데_성공한다() {
        // given
        long storeId = 1L;
        long userId = 1L;

        Dibs dibs = Dibs.CreateDibs(storeId, userId);

        when(dibsRepository.deleteByIdStoreIdAndIdUserId(storeId, userId)).thenReturn(Optional.of(dibs));

        // when
        String result = dibsService.deleteDibs(storeId, userId);


        // then
        assertThat(result).isEqualTo("해당 가게를 찜 목록에서 삭제하였습니다.");
        verify(dibsRepository, times(1)).deleteByIdStoreIdAndIdUserId(storeId, userId);
    }

    @Test
    void 내가_찜한_가게_목록을_조회하는데_성공한다() {
        // given
        long userId = 1L;
        User user = new User();
        ReflectionTestUtils.setField(user, "id", userId);

        long storeId1 = 1L;
        long storeId2 = 2L;

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

        Store store1 = Store.createStore(requestDto1, user);
        ReflectionTestUtils.setField(store1, "id", storeId1);


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

        Store store2 = Store.createStore(requestDto2, user);
        ReflectionTestUtils.setField(store2, "id", storeId2);

        Dibs dibs1 = Dibs.CreateDibs(storeId1, userId);
        Dibs dibs2 = Dibs.CreateDibs(storeId2, userId);

        given(storeRepository.findById(storeId1)).willReturn(Optional.of(store1)); // store1에 대한 mock 추가
        given(storeRepository.findById(storeId2)).willReturn(Optional.of(store2)); // store2에 대한 mock 추가

        given(dibsRepository.findByIdUserId(anyLong())).willReturn(List.of(dibs1, dibs2));

        // when
        List<StoreResponseDto> result = dibsService.getDips(userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(storeId1);
        assertThat(result.get(1).getId()).isEqualTo(storeId2);



    }















}
