package com.example.trello.config;

import com.example.trello.config.interceptor.BoardInterceptor;
import com.example.trello.config.interceptor.ReadOnlyInterceptor;
import com.example.trello.config.interceptor.WorkSpaceInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfigImpl implements WebMvcConfigurer {

    private static final String[] AUTH_REQUIRED_PATH_PATTERNS = {"/users/login", "/users/sign-up", "/workspaces"};
    private static final String[] WORKSPACE_ROLE_REQUIRED_PATH_PATTERNS = {""};
    private static final String[] BOARD_ROLE_REQUIRED_PATH_PATTERNS = {""};
    private static final String[] READ_ONLY_ROLE_REQUIRED_PATH_PATTERNS = {""};

    private final WorkSpaceInterceptor workSpaceInterceptor;
    private final BoardInterceptor boardInterceptor;
    private final ReadOnlyInterceptor readOnlyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(workSpaceInterceptor)
                .addPathPatterns(WORKSPACE_ROLE_REQUIRED_PATH_PATTERNS)
                .excludePathPatterns(AUTH_REQUIRED_PATH_PATTERNS)
                .order(Ordered.HIGHEST_PRECEDENCE);
        registry.addInterceptor(boardInterceptor)
                .addPathPatterns(BOARD_ROLE_REQUIRED_PATH_PATTERNS)
                .excludePathPatterns(AUTH_REQUIRED_PATH_PATTERNS)
                .order(Ordered.HIGHEST_PRECEDENCE + 1);
        registry.addInterceptor(readOnlyInterceptor)
                .addPathPatterns(READ_ONLY_ROLE_REQUIRED_PATH_PATTERNS)
                .excludePathPatterns(AUTH_REQUIRED_PATH_PATTERNS)
                .order(Ordered.HIGHEST_PRECEDENCE + 2);
    }
}
