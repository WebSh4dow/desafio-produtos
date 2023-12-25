package com.gerenciamento.produtos.model.assembler;

import com.gerenciamento.produtos.controller.ProdutoController;
import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.model.representation.ProdutoRepresentationModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProdutoAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoRepresentationModel> {


    public ProdutoAssembler() {
        super(ProdutoController.class, ProdutoRepresentationModel.class);
    }

    @Override
    public ProdutoRepresentationModel toModel(Produto entity) {
       ProdutoRepresentationModel representation = instantiateModel(entity);

        representation.setId(entity.getId());
        representation.setNome(entity.getNome());
        representation.setIcms(entity.getIcms());
        representation.setSku(entity.getSku());
        representation.setAtivo(entity.isAtivo());
        representation.setCategoria(entity.getCategoria());
        representation.setValorCusto(entity.getValorCusto());
        representation.setValorVenda(entity.getValorVenda());
        representation.setDataCadastro(entity.getDataCadastro());
        representation.setQuantidadeEstoque(entity.getQuantidadeEstoque());

       return representation;
    }

    @Override
    public CollectionModel<ProdutoRepresentationModel> toCollectionModel(Iterable<? extends Produto> entities) {
        CollectionModel<ProdutoRepresentationModel> representations = super.toCollectionModel(entities);
        return representations;
    }


    private List<ProdutoRepresentationModel> toProdutoModel(List<Produto> produtos) {
        if (produtos.isEmpty())
            return Collections.emptyList();

        return produtos.stream()
                .map(produtoMap -> ProdutoRepresentationModel.builder()
                        .id(produtoMap.getId())
                        .nome(produtoMap.getNome())
                        .icms(produtoMap.getIcms())
                        .sku(produtoMap.getSku())
                        .ativo(produtoMap.isAtivo())
                        .valorVenda(produtoMap.getValorVenda())
                        .quantidadeEstoque(produtoMap.getQuantidadeEstoque())
                        .categoria(produtoMap.getCategoria())
                        .dataCadastro(produtoMap.getDataCadastro())
                        .valorCusto(produtoMap.getValorCusto())
                        .build()
                        .add(linkTo(
                                methodOn(ProdutoController.class)
                                        .buscarProdutoPorId(produtoMap.getId()))
                                .withSelfRel()))
                .collect(Collectors.toList());
    }
}
