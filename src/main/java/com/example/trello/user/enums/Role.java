package com.example.trello.user.enums;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
public enum Role {
    USER("USER"),
    ADMIN("ADMIN")

    ;
    private final String name;

    Role(String name) {
        this.name = name;
    }

    public static Role of(String roleName) throws IllegalArgumentException {
        for (Role role : values()) {
            if (role.getName().equals(roleName.toUpperCase())) {
                return role;
            }
        }

        throw new RuntimeException();
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name));
    }
 }
