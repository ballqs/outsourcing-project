//package org.sparta.outsourcingproject.domain.user.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.sparta.outsourcingproject.domain.user.Authority;
//import org.sparta.outsourcingproject.domain.user.dto.PostUserSignUpRequestDto;
//import org.sparta.outsourcingproject.domain.user.entity.User;
//import org.sparta.outsourcingproject.domain.user.repository.UserRepository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Test
//    void 유저_등록에_성공한다(){
//
//        PostUserSignUpRequestDto requestDto = new PostUserSignUpRequestDto(
//                "Test@test.com",
//                "홍길동",
//                "abcD123!",
//                "000-0000-0000",
//                Authority.OWNER,
//                "012345",
//                "00시 00구 00동",
//                "00아파트 00동 000호"
//        );
//
//        given(userRepository.existsByEmail(requestDto.getEmail()));
//
//        User user = user.crea
//        //then
//        assertThat(result.getName()).isEqualTo(user.getName());
//        assertThat(result.getAddress()).isEqualTo(store.getAddress());
//        assertThat(result.getTel()).isEqualTo(store.getTel());
//
//    }
//}
