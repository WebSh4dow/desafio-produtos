package com.gerenciamento.produtos.security.model;

import com.gerenciamento.produtos.model.Usuario;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "refreshtoken")
@ApiModel(description = "Modelo de representação do refreshToken")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "id do refreshtoken", example = "1")
    private long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @ApiModelProperty(notes = "Usuário associado ao refreshToken")
    private Usuario usuario;

    @Column(nullable = false, unique = true)
    @ApiModelProperty(notes = "token gerado", example = "43424fdsfsd-432423-324fdsf")
    private String token;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Data de expiração do refreshToken", example = "2023-12-31T23:59:59Z")
    private Instant dataExpiracao;

    public RefreshToken() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Instant dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

}