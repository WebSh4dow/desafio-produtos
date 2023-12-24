package com.gerenciamento.produtos.repository;

import com.gerenciamento.produtos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findByLogin(String login);

    Boolean existsByLogin(String login);

    @Query(value = "SELECT constraint_name FROM information_schema.constraint_column_usage WHERE table_name = 'usuarios_acesso' AND column_name = 'acesso_id' AND constraint_name <> 'unique_acesso_user';", nativeQuery = true)
    List<String> consultarConstraintAcesso();

}
