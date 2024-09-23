//package org.sparta.outsourcingproject.domain.store.controller;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.sparta.outsourcingproject.common.dto.ResponseDto;
//import org.sparta.outsourcingproject.domain.store.dto.request.StoreCreateRequestDto;
//import org.sparta.outsourcingproject.domain.store.dto.request.StoreListRequestDto;
//import org.sparta.outsourcingproject.domain.store.dto.request.StoreUpdateRequestDto;
//import org.sparta.outsourcingproject.domain.store.dto.response.StoreCreateResponseDto;
//import org.sparta.outsourcingproject.domain.store.dto.response.StoreGetResponseDto;
//import org.sparta.outsourcingproject.domain.store.dto.response.StoreResponseDto;
//import org.sparta.outsourcingproject.domain.store.entity.Store;
//import org.sparta.outsourcingproject.domain.store.enums.StoreOperationStatus;
//import org.sparta.outsourcingproject.domain.store.service.StoreService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.math.BigDecimal;
//import java.time.LocalTime;
//import java.util.List;
//
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doNothing;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//@WebMvcTest(StoreController.class)
//class StoreControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private StoreService storeService;
//
//    @Test
//    @DisplayName("가게 등록 성공")
//    void createStore_success() throws Exception {
//        // given
//
//
//
//        StoreCreateRequestDto requestDto = new StoreCreateRequestDto(
//                "Test2",
//                "chicken",
//                "123-4357",
//                LocalTime.of(8, 0),
//                LocalTime.of(23, 0),
//                20000,
//                "city2",
//                StoreOperationStatus.OPERATE,
//                BigDecimal.valueOf(0.0)
//        );
//
//        Store.createStore(requestDto);
//
//        StoreCreateResponseDto respoonse = StoreCreateResponseDto.of(requestDto);
//
//        given(storeService.createStore(ArgumentMatchers.any(StoreCreateRequestDto.class), ArgumentMatchers.anyLong()))
//                .willReturn(responseDto);
//
//        // when
//        ResultActions result = mockMvc.perform(post("/api/stores")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"name\":\"Test Store\",\"category\":\"pizza\",\"phone\":\"123-4567\",\"minOrderAmount\":10000,\"address\":\"Seoul\",\"addressDetail\":\"Address Detail\"}")
//                .header("userId", 1L));
//
//        // then
//        result.andExpect(status().isCreated())
//                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
//                .andExpect(jsonPath("$.data.id").value(1L))
//                .andExpect(jsonPath("$.data.name").value("Test Store"))
//                .andExpect(jsonPath("$.message").value("가게 등록에 성공하였습니다!"))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("가게 단건 조회 성공")
//    void getStore_success() throws Exception {
//        // given
//        StoreGetResponseDto responseDto = new StoreGetResponseDto(1L, "Test Store", "pizza");
//
//        given(storeService.getStore(1L)).willReturn(responseDto);
//
//        // when
//        ResultActions result = mockMvc.perform(get("/api/stores/{storeId}", 1L)
//                .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        result.andExpect(status().isCreated())
//                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
//                .andExpect(jsonPath("$.data.id").value(1L))
//                .andExpect(jsonPath("$.data.name").value("Test Store"))
//                .andExpect(jsonPath("$.message").value("가게 단건 조회에 성공하였습니다!"))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("가게 다건 조회 성공")
//    void getStores_success() throws Exception {
//        // given
//        StoreResponseDto responseDto1 = new StoreResponseDto(1L, "Test Store 1");
//        StoreResponseDto responseDto2 = new StoreResponseDto(2L, "Test Store 2");
//
//        given(storeService.getStores(ArgumentMatchers.any(StoreListRequestDto.class)))
//                .willReturn(List.of(responseDto1, responseDto2));
//
//        // when
//        ResultActions result = mockMvc.perform(get("/api/stores")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"name\":\"Test\"}"));
//
//        // then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
//                .andExpect(jsonPath("$.data").isArray())
//                .andExpect(jsonPath("$.data[0].id").value(1L))
//                .andExpect(jsonPath("$.data[1].id").value(2L))
//                .andExpect(jsonPath("$.message").value("해당 이름을 포함하는 모든 가게 조회에 성공하였습니다."))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("가게 정보 수정 성공")
//    void updateStoreInfo_success() throws Exception {
//        // given
//        StoreGetResponseDto responseDto = new StoreGetResponseDto(1L, "Updated Store", "chicken");
//
//        given(storeService.updateStoreInfo(ArgumentMatchers.any(StoreUpdateRequestDto.class), ArgumentMatchers.anyLong()))
//                .willReturn(responseDto);
//
//        // when
//        ResultActions result = mockMvc.perform(patch("/api/stores/{storeId}", 1L)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"name\":\"Updated Store\",\"category\":\"chicken\"}"));
//
//        // then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
//                .andExpect(jsonPath("$.data.id").value(1L))
//                .andExpect(jsonPath("$.data.name").value("Updated Store"))
//                .andExpect(jsonPath("$.message").value("가게 정보가 성공적으로 수정되었습니다."))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("가게 폐업 처리 성공")
//    void shutdownStore_success() throws Exception {
//        // given
//        doNothing().when(storeService).setShutdownStore(1L);
//
//        // when
//        ResultActions result = mockMvc.perform(patch("/api/stores/shutdown/{storeId}", 1L)
//                .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
//                .andExpect(jsonPath("$.message").value("해당 가게가 폐업 상태로 전환되었습니다"))
//                .andDo(print());
//    }
//}
