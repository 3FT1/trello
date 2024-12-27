package com.example.trello.user;

import com.example.trello.user.dto.JwtAuthResponse;
import com.example.trello.user.dto.LoginRequestDto;
import com.example.trello.user.dto.SignupRequestDto;
import com.example.trello.user.enums.AccountStatus;
import com.example.trello.util.AuthenticationScheme;
import com.example.trello.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    public void signupUser(SignupRequestDto requestDto) {
        Optional<User> findUser = userRepository.findByEmail(requestDto.getEmail());

        if (findUser.isPresent()) {

            if (AccountStatus.DELETED.equals(findUser.get().getStatus())) {
                throw new RuntimeException();
            }
            throw new RuntimeException();
        }

        String encodedPassword = bCryptPasswordEncoder.encode(requestDto.getPassword());

        User user = User.builder().
                email(requestDto.getEmail())
                .password(encodedPassword)
                .nickname(requestDto.getNickname())
                .role(requestDto.getRole())
                .status(AccountStatus.ACTIVE).build();

        userRepository.save(user);
    }


    public JwtAuthResponse login(LoginRequestDto requestDto) {
        User findUser = userRepository.findByEmailOrElseThrow(requestDto.getEmail());

        if (findUser.isDeletedAccount(findUser)) {
            throw new RuntimeException();
        }

        this.validatePassword(requestDto.getPassword(), findUser.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword())
        );
        log.info("SecurituContext에 Authentication 저장");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtProvider.generateToken(authentication);
        log.info("토큰 생성: {}",accessToken);

        return new JwtAuthResponse(AuthenticationScheme.BEARER.getName(), accessToken);
    }

    @Transactional
    public void leave(Long userId) {
        User findUser = userRepository.findByIdOrElseThrow(userId);
        findUser.deletedAccount(AccountStatus.DELETED);
    }

    private void validatePassword(String rawPassword, String encodedPassword)
        throws RuntimeException {
        boolean notValid = !bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
        if (notValid) {
            throw new RuntimeException();
        }
    }
}
