package com.example.trello.user;

import com.example.trello.user.dto.LoginRequestDto;
import com.example.trello.user.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> login(
            @RequestBody LoginRequestDto requestDto,
            HttpServletRequest httpServletRequest
    ) {
        User loginUser = userService.login(requestDto);

        HttpSession session = httpServletRequest.getSession();

        if (session.getAttribute("id") != null) {
            throw new RuntimeException();
        }

        session.setAttribute("id", loginUser);
        return ResponseEntity.ok().body("정상적으로 로그인 되었습니다.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok().body("로그아웃 되었습니다.");
    }

    @DeleteMapping("/leave")
    public ResponseEntity<String> leave(
            @SessionAttribute("id") User user,
            HttpServletRequest httpServletRequest
    ) {
        userService.leave(user.getId());

        HttpSession session = httpServletRequest.getSession();
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok().body("회원 탈퇴가 정상적으로 처리되었습니다.");
    }
}
