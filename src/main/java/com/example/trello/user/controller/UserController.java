package com.example.trello.user.controller;

import com.example.trello.user.User;
import com.example.trello.user.UserService;
import com.example.trello.user.dto.JwtAuthResponse;
import com.example.trello.user.dto.LoginRequestDto;
import com.example.trello.user.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signupUser(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signupUser(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(
            @Valid @RequestBody LoginRequestDto requestDto
    ) {
        JwtAuthResponse authResponse = this.userService.login(requestDto);

        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Authentication authentication
            ) throws RuntimeException {
        if (authentication != null && authentication.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, null);

            return ResponseEntity.ok().body("로그아웃 되었습니다.");
        }
        throw new RuntimeException();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> leave(
            @PathVariable Long userId,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Authentication authentication
    ) throws RuntimeException {
        userService.leave(userId);

        if (authentication != null && authentication.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, null);
        }

        return ResponseEntity.ok().body("회원 탈퇴가 정상적으로 처리되었습니다.");
    }
}
