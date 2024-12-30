package com.example.trello.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j(topic = "Security::SecurityConfig")
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

//    private static final String[] WHITE_LIST = {"/users/login", "/users/sign-up"};


    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager(인증 관리자).
     *
     * @param config {@link AuthenticationConfiguration}
     * @return 설정이 추가된 AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        log.info("AuthenticationManager에 위임.");
        return config.getAuthenticationManager();
    }

    /**
     * AuthenticationProvider(인증 공급자).
     *
     * @return {@link AuthenticationProvider}
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        log.info("AuthenticationProvider 설정. 구현체: {}", authProvider.getClass().getSimpleName());

        log.info("UserDetailsService에 사용자 관리 위임. 구현체: {}",
                this.userDetailsService.getClass().getSimpleName());
        authProvider.setUserDetailsService(this.userDetailsService);

        log.info("PasswordEncoder에 암호 검증 위임. 구현체: {}",
                this.passwordEncoder().getClass().getSimpleName());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
