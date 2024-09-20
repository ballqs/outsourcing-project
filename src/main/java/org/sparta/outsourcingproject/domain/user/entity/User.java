package org.sparta.outsourcingproject.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.outsourcingproject.common.entity.Timestamped;
import org.sparta.outsourcingproject.domain.user.dto.PostUserSaveRequestDto;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;
    private String name;
    private String pw;
    @Column(name = "phoneNumber", unique = true)
    private String phoneNumber;

    public User(PostUserSaveRequestDto requestDto, String pw) {
        this.email = requestDto.getEmail();
        this.name = requestDto.getName();
        this.pw = pw;
        this.phoneNumber = requestDto.getPhoneNumber();
    }
}
