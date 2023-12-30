package com.gerenciamento.produtos.security.checker;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class AuthorizationChecker {

    public static boolean isEstoquista(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .anyMatch(authority -> "ESTOQUISTA".equals(authority.getAuthority()));
    }

    public static Collection<? extends GrantedAuthority> obterAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getAuthorities() != null) {
            return authentication.getAuthorities();
        } else {
            return Collections.emptyList();
        }
    }


}
