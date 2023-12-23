package com.gerenciamento.produtos.security.provider;

import com.gerenciamento.produtos.exception.TokenRefreshException;
import com.gerenciamento.produtos.model.Usuario;
import com.gerenciamento.produtos.repository.RefreshTokenRepository;
import com.gerenciamento.produtos.repository.UsuarioRepository;
import com.gerenciamento.produtos.security.model.RefreshToken;
import com.gerenciamento.produtos.security.service.SecurityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class RefreshTokenProvider implements SecurityService {

    @Value("${refresh.token}")
    int refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public Optional<RefreshToken> findByToken(String token) {
        if (StringUtils.isBlank(token)) {
            throw new RuntimeException("O token atualmente não é valido ou é nulo");
        }
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(Long usuarioId) {
        RefreshToken refreshToken = new RefreshToken();

        Optional<Usuario> usuarioAtual = usuarioRepository.findById(usuarioId);

        if (!usuarioAtual.isPresent()) {
            throw new RuntimeException("Usuario atual não existe no banco de dados.");
        }
        else {
            refreshToken.setUsuario(usuarioAtual.get());
            refreshToken.setDataExpiracao(Instant.now().plusMillis(refreshTokenDurationMs));
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken = refreshTokenRepository.save(refreshToken);
        }
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getDataExpiracao().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token expirou. Porfavor faça uma nova requisição de login.");
        }
        return token;
    }

    @Override
    @Transactional
    public int deleteByUserId(Long usuarioId) {
        return refreshTokenRepository.deleteByUsuario(usuarioRepository.findById(usuarioId).get());
    }
}
