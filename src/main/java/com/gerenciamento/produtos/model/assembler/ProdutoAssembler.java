package com.gerenciamento.produtos.model.assembler;

import com.gerenciamento.produtos.controller.ProdutoController;
import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.model.representation.ProdutoRepresentationModel;
import com.gerenciamento.produtos.security.checker.AuthorizationChecker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProdutoAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoRepresentationModel> {

    @Autowired
    private AuthorizationChecker authorizationChecker;

    public ProdutoAssembler() {
        super(ProdutoController.class, ProdutoRepresentationModel.class);
    }

    @Override
    public ProdutoRepresentationModel toModel(Produto entity) {
        Collection<? extends GrantedAuthority> authorities = AuthorizationChecker.obterAuthorities();
        return createModelWithId(entity.getId(), entity, authorities);
    }

    @Override
    protected ProdutoRepresentationModel instantiateModel(Produto entity) {
        Collection<? extends GrantedAuthority> authorities = AuthorizationChecker.obterAuthorities();
        return new ProdutoRepresentationModel(entity, authorities);
    }




}
