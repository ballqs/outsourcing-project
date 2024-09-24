
package org.sparta.outsourcingproject.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.domain.user.Authority;

@Getter
@AllArgsConstructor
public class PostUserSignUpRequestDto {
    private String email;
    private String name;
    private String pw;
    private String phoneNumber;
    private Authority authority;
    private String zip;
    private String address;
    private String addressDetail;

}
