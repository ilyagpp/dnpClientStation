package com.example.dnpclientstation.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, AZS_ADMIN, TERMINAL;

    @Override
    public String getAuthority() {
        return name();
    }
}
