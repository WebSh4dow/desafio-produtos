package com.gerenciamento.produtos.security.service;

import com.gerenciamento.produtos.security.model.RefreshToken;
import java.util.Optional;

public interface SecurityService {
    Optional<RefreshToken> findByToken(String token);
    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyExpiration(RefreshToken token);

    int deleteByUserId(Long userId);
}
