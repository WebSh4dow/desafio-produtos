package com.gerenciamento.produtos.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "usuario")
@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1, initialValue = 1)
@ApiModel(description = "Modelo de representação de Usuários")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @ApiModelProperty(notes = "id do usuário", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @ApiModelProperty(notes = "nome do usuário ou nickname", example = "javaNextGenDeveloper")
    private String login;

    @Column(nullable = false)
    @ApiModelProperty(notes = "senha do usuário", example = "jadGenDev572")
    private String senha;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "usuarios_acesso",
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "acesso_id"}, name = "unique_acesso_user"),
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id", table = "usuario", unique = false,
                    foreignKey = @ForeignKey(name = "usuario_fk", value = ConstraintMode.CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "acesso_id", unique = false, referencedColumnName = "id", table = "acesso",
                    foreignKey = @ForeignKey(name = "acesso_fk", value = ConstraintMode.CONSTRAINT))
    )
    @ApiModelProperty(
            notes = "Acessos do usuário",
            example = "[{\"id\": 1, \"descricao\": \"NORMAL\"}]"
    )
    private Set<Acesso> acessos = new HashSet<>();

    public Usuario(String login, String senha) {
        this.senha = senha;
        this.login = login;
    }

    public Usuario() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.acessos;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setAcessos(Set<Acesso> acessos) {
        this.acessos = acessos;
    }

    public Set<Acesso> getAcessos() {
        return acessos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void addAcesso(Acesso acesso) {
        this.acessos.add(acesso);
    }

    public void removeAcesso(Acesso acesso) {
        this.acessos.remove(acesso);
    }
}
