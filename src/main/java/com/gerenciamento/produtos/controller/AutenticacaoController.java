package com.gerenciamento.produtos.controller;

import com.gerenciamento.produtos.exception.BussinesException;
import com.gerenciamento.produtos.exception.TokenRefreshException;
import com.gerenciamento.produtos.model.Acesso;
import com.gerenciamento.produtos.model.Usuario;
import com.gerenciamento.produtos.openApiController.AutenticacaoControllerOpenApi;
import com.gerenciamento.produtos.repository.AcessoRepository;
import com.gerenciamento.produtos.repository.UsuarioRepository;
import com.gerenciamento.produtos.security.model.RefreshToken;
import com.gerenciamento.produtos.security.provider.JwtTokenProvider;
import com.gerenciamento.produtos.security.provider.RefreshTokenProvider;
import com.gerenciamento.produtos.security.request.LoginRequest;
import com.gerenciamento.produtos.security.request.SignupRequest;
import com.gerenciamento.produtos.security.request.SignupSimpleRequest;
import com.gerenciamento.produtos.security.request.TokenRefreshRequest;
import com.gerenciamento.produtos.security.response.JwtResponse;
import com.gerenciamento.produtos.security.response.TokenRefreshResponse;
import com.gerenciamento.produtos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController implements AutenticacaoControllerOpenApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RefreshTokenProvider refreshTokenService;

    private static final String ACESSO_NORMAL = "NORMAL";

    private static final String ACESSO_ESTOQUISTA = "ESTOQUISTA";

    private static final String ACESSO_ADMINISTRADOR = "ADMINISTRADOR";

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> autenticarUsuario(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getSenha())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario userDetails = (Usuario) authentication.getPrincipal();

        String jwt = jwtTokenProvider.generateToken(authentication);

        List<Acesso> acessos = userDetails.getAuthorities().stream()
                .map(acessoUsuario -> (Acesso) acessoUsuario)
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getLogin(), acessos));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registrarUsuario(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByLogin(signUpRequest.getLogin())) {
            return ResponseEntity.badRequest().body("Error Usuário atual ja possui um refresh Token!");
        }

        Usuario usuario = new Usuario(signUpRequest.getLogin(),
                encoder.encode(signUpRequest.getSenha()));

        Set<Acesso> acessos = signUpRequest.getAcessos();
        Set<Acesso> acessoUsuarioSet = new HashSet<>();

        if (acessos == null) {
            Acesso acessoUsuario = acessoRepository.findByDescricao(ACESSO_NORMAL)
                    .orElseThrow(() -> new BussinesException("Error: Acesso Atual não existe no cadastro."));
            acessoUsuarioSet.add(acessoUsuario);
        } else {
            acessos.forEach(acessoCadastro -> {
                if (ACESSO_ADMINISTRADOR.equals(acessoCadastro.toString())) {
                    Acesso acessoAdministrador = acessoRepository.findByDescricao(ACESSO_ADMINISTRADOR)
                            .orElseThrow(() -> new BussinesException("Error: Acesso Atual não existe no cadastro."));
                    acessoUsuarioSet.add(acessoAdministrador);
                }

                if (ACESSO_ESTOQUISTA.equals(acessoCadastro.toString())) {
                    Acesso AcessoEstoquista = acessoRepository.findByDescricao(ACESSO_ESTOQUISTA)
                            .orElseThrow(() -> new BussinesException("Erro: Acesso Atual não existe no cadastro."));
                    acessoUsuarioSet.add(AcessoEstoquista);
                } else {
                    Acesso acessoPadrao = acessoRepository.findByDescricao(ACESSO_NORMAL)
                            .orElseThrow(() -> new BussinesException("Erro:  Acesso Atual não existe no cadastro."));
                    acessoUsuarioSet.add(acessoPadrao);
                }
            });
        }

        usuarioService.consultarConstraintAndRemove();

        usuario.setAcessos(acessoUsuarioSet);

        userRepository.saveAndFlush(usuario);

        return ResponseEntity.ok("Usuario com o nome de " + signUpRequest.getLogin() + " Foi Cadastrado com sucesso!");
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenRefreshResponse> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUsuario)
                .map(user -> {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());

                    String token = jwtTokenProvider.generateToken(authentication);
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token não existe atualmente no banco de dados!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<String> logoutUser() {
        Usuario userDetails = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok("Log out realizado com sucesso!");
    }
}
