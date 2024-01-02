package com.gerenciamento.produtos.repository.projections;

import com.gerenciamento.produtos.model.representation.ProdutoAgregadoRepresentationModel;
import java.math.BigDecimal;

public interface ProdutoAgregadoProjection {

    Long getProdutoId();
    String getProdutoNome();
    BigDecimal getValorCusto();
    BigDecimal getValorVenda();
    Integer getQuantidadeEstoque();
    BigDecimal getValorTotalPrevisto();

    default ProdutoAgregadoRepresentationModel toRepresentationModel() {
        ProdutoAgregadoRepresentationModel representationModel = new ProdutoAgregadoRepresentationModel();
        representationModel.setId(getProdutoId());
        representationModel.setNome(getProdutoNome());
        representationModel.setCustoTotal(getValorCusto());
        representationModel.setValorTotalPrevisto(getValorVenda());
        representationModel.setQuantidade(getQuantidadeEstoque());
        representationModel.setValorTotalPrevisto(getValorTotalPrevisto());
        return representationModel;
    }
}
