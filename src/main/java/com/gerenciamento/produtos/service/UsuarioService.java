package com.gerenciamento.produtos.service;

import com.gerenciamento.produtos.exception.BussinesException;
import com.gerenciamento.produtos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("login não encontrado no banco de dados: " + login));
    }

    public void consultarConstraintAndRemove() {
        try {
            List<String> constraints = usuarioRepository.consultarConstraintAcesso();
            if (constraints != null && !constraints.isEmpty()) {
                for (String constraint : constraints) {
                    jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
                }
            }
        } catch (Exception e) {
            throw new BussinesException(e.getMessage());
        }
    }
}
