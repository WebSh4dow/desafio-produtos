package com.gerenciamento.produtos.model;

import com.gerenciamento.produtos.enums.Tipo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "categoria")
@SequenceGenerator(name = "seq_categoria", sequenceName = "seq_categoria",allocationSize = 1, initialValue = 1)
@ApiModel(description = "Modelo de representação de Categorias")
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_categoria")
    @ApiModelProperty(notes = "id da Categoria", example = "1")
    private Long id;

    @NotBlank(message = "Nome da categoria deve ser obrigátorio")
    @ApiModelProperty(notes = "nome da categoria cadastrada ", example = "PERSONALIZADA,NORMAL")
    private String nome;

    @ApiModelProperty(notes = "Status da categoria inativo ou ativo", example = "true,false")
    private boolean ativo;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "Tipo da categoria", example = "NORMAL,ESPECIAL,PERSONALIZADO")
    private Tipo tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(id, categoria.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
