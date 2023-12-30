package com.gerenciamento.produtos.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "produto")
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto",allocationSize = 1, initialValue = 1)
@ApiModel(description = "Modelo de representação de produtos")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_produto")
    @ApiModelProperty(notes = "id do produto", example = "1")
    private Long id;

    @NotBlank(message = "O nome do produto é um campo obrigatório")
    @ApiModelProperty(notes = "nome do produto", example = "Saboneteira AZUL")
    private String nome;

    @NotBlank(message = "O SKU é um campo obrigatório")
    @ApiModelProperty(notes = "sku do produto", example = "Saboneteira AZUL")
    private String sku;

    @ManyToOne
    @JoinColumn(name = "categoria_id",
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "categoria_fk"))
    @ApiModelProperty(
            notes = "Informações sobre a categoria",
            example = "{\"id\": 1, \"nome\": \"Acessorios\", \"ativo\": true, \"tipo\": \"PERSONALIZADO\"}")
    private Categoria categoria;

    @ApiModelProperty(
            notes = "Informações sobre o valor de custo",
            example = "10.00")
    private BigDecimal valorCusto = BigDecimal.ZERO;

    @ApiModelProperty(
            notes = "Informações sobre o valor de venda",
            example = "100.00")
    private BigDecimal valorVenda = BigDecimal.ZERO;

    @ApiModelProperty(
            notes = "Informações sobre a quantidade em estoque",
            example = "100")
    private Integer quantidadeEstoque;

    @ApiModelProperty(
            notes = "Informações sobre o icms calculado sobre o produto",
            example = "10.00")
    private BigDecimal icms = BigDecimal.ZERO;

    @ApiModelProperty(
            notes = "Informações sobre a data de cadastro do produto",
            example = "2023-12-12")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataCadastro;

    @ApiModelProperty(
            notes = "Informações sobre status ativo ou inativo do produto",
            example = "true")
    private boolean ativo;

    @ApiModelProperty(
            notes = "Informações sobre campos visiveis ativo ou inativo do produto",
            example = "true")


    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getValorCusto() {
        return valorCusto;
    }

    public void setValorCusto(BigDecimal valorCusto) {
        this.valorCusto = valorCusto;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public BigDecimal getIcms() {
        return icms;
    }

    public void setIcms(BigDecimal icms) {
        this.icms = icms;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
