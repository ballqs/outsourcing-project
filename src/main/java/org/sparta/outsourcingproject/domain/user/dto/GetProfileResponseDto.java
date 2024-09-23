package org.sparta.outsourcingproject.domain.user.dto;

import lombok.Getter;
import org.sparta.outsourcingproject.domain.user.Authority;
import org.sparta.outsourcingproject.domain.user.entity.User;
import org.springframework.web.service.invoker.UrlArgumentResolver;

@Getter
public class GetProfileResponseDto {
    private final String email;
    private final String name;
    private final String phoneNumber;
    private final Authority authority;
    private final String zip;
    private final String address;
    private final String addressDetail;

    public GetProfileResponseDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.authority = user.getAuthority();
        this.zip =user.getZip();
        this.address = user.getAddress();
        this.addressDetail = user.getAddressDetail();
    }
}
