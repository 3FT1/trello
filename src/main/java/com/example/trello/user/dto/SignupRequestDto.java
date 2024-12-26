package com.example.trello.user.dto;

import com.example.trello.user.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotBlank(message = "이메일은 필수값 입니다.")
    @Pattern(regexp = "^[A-Za-z0-9.!@#$+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다. 다시 입력해주세요")
    private final String email;

    @NotBlank(message = "비밀번호는 필수값 입니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$._+]).{8,16}$" , message = "비밀번호는 대문자 + 소문자 + 숫자 + 특수문자를 최소 1글자씩 입려해주세요")
    private final String password;

    @NotBlank(message = "닉네임은 필수값 입니다.")
    private final String nickname;

    @NotNull(message = "유저 역할은 필수값 입니다.")
    private final Role role;


    @Builder
    public SignupRequestDto(String email, String password, String nickname, Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }
}
