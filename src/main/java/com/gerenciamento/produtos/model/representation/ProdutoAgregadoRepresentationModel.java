package com.gerenciamento.produtos.model.representation;

import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.security.checker.AuthorizationChecker;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.security.core.GrantedAuthority;
import java.math.BigDecimal;
import java.util.Collection;


@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "produtosAgragados")
@Relation(collectionRelation = "produtosAgragados")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Modelo de representação de valores agregados ao produto")
public class ProdutoAgregadoRepresentationModel extends RepresentationModel<ProdutoAgregadoRepresentationModel> {

    @ApiModelProperty(notes = "id do produto agregado", example = "1")
    private Long id;

    @ApiModelProperty(notes = "nome do produto agregado", example = "saboneteira")
    private String nome;

    @ApiModelProperty(notes = "custo total do produto agregado", example = "10.00")
    private BigDecimal custoTotal;

    @ApiModelProperty(notes = "quantidade de produtos agregados", example = "10")
    private Integer quantidade;

    @ApiModelProperty(notes = "valor total prestido do produto agregado", example = "10.00")
    private BigDecimal valorTotalPrevisto;

    public ProdutoAgregadoRepresentationModel() {
    }

    public ProdutoAgregadoRepresentationModel(Produto produto, Collection<? extends GrantedAuthority> authorities) {
        this();

        boolean isEstoquista = AuthorizationChecker.isEstoquista(authorities);

        if (isEstoquista) {
            this.custoTotal = BigDecimal.ZERO;
            this.valorTotalPrevisto = BigDecimal.ZERO;
        }
    }


}
