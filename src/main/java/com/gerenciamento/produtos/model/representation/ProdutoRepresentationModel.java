package com.gerenciamento.produtos.model.representation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.gerenciamento.produtos.model.Categoria;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "produtos")
@Relation(collectionRelation = "produtos")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Modelo de representação de produtos")
public class ProdutoRepresentationModel extends RepresentationModel<ProdutoRepresentationModel> {

    @ApiModelProperty(notes = "id do produto", example = "1")
    private Long id;

    @ApiModelProperty(notes = "nome do produto", example = "Saboneteira AZUL")
    private String nome;

    @ApiModelProperty(notes = "sku do produto", example = "Saboneteira AZUL")
    private String sku;

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
    private BigDecimal icms = BigDecimal.ZERO;;

    @ApiModelProperty(
            notes = "Informações sobre a data de cadastro do produto",
            example = "2023-12-12")
    private Date dataCadastro;

    @ApiModelProperty(
            notes = "Informações sobre status ativo ou inativo do produto",
            example = "true")
    private boolean ativo;

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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
