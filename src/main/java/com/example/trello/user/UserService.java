package com.example.trello.user;

import com.example.trello.user.dto.LoginRequestDto;
import com.example.trello.user.dto.SignupRequestDto;
import com.example.trello.user.enums.AccountStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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


    public User login(LoginRequestDto requestDto) {
        User findUser = userRepository.findByEmailOrElseThrow(requestDto.getEmail());

        if (findUser.getStatus().equals(AccountStatus.DELETED)) {
            throw new RuntimeException();
        }

        String encodedPassword = bCryptPasswordEncoder.encode(requestDto.getPassword());

        if (bCryptPasswordEncoder.matches(findUser.getPassword(), encodedPassword)) {
            throw new RuntimeException();
        }

        return findUser;
    }

    @Transactional
    public void leave(Long userId) {
        User findUser = userRepository.findByIdOrElseThrow(userId);
        findUser.deletedAccount(AccountStatus.DELETED);
    }
}
