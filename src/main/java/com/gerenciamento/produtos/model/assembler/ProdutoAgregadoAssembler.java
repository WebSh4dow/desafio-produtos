package com.gerenciamento.produtos.model.assembler;

import com.gerenciamento.produtos.controller.ProdutoController;
import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.model.representation.ProdutoAgregadoRepresentationModel;
import com.gerenciamento.produtos.security.checker.AuthorizationChecker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;

@Component
public class ProdutoAgregadoAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoAgregadoRepresentationModel> {

    @Autowired
    private AuthorizationChecker authorizationChecker;

    public ProdutoAgregadoAssembler() {
        super(ProdutoController.class, ProdutoAgregadoRepresentationModel.class);
    }

    @Override
    public ProdutoAgregadoRepresentationModel toModel(Produto produto) {
        ProdutoAgregadoRepresentationModel produtoAgregado = new ProdutoAgregadoRepresentationModel();

        BeanUtils.copyProperties(produto, produtoAgregado);

        Collection<? extends GrantedAuthority> authorities = AuthorizationChecker.obterAuthorities();

        boolean isEstoquista = AuthorizationChecker.isEstoquista(authorities);

        if (!isEstoquista) {
            produtoAgregado.setCustoTotal(null);
            produtoAgregado.setValorTotalPrevisto(null);
        }

        return produtoAgregado;
    }

    @Override
    protected ProdutoAgregadoRepresentationModel instantiateModel(Produto entity) {
        Collection<? extends GrantedAuthority> authorities = AuthorizationChecker.obterAuthorities();
        return new ProdutoAgregadoRepresentationModel(entity, authorities);
    }
}