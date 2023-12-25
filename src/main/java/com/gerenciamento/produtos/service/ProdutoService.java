package com.gerenciamento.produtos.service;

import com.gerenciamento.produtos.exception.BussinesException;
import com.gerenciamento.produtos.model.Categoria;
import com.gerenciamento.produtos.model.Produto;
import com.gerenciamento.produtos.repository.CategoriaRepository;
import com.gerenciamento.produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final String PRODUTO_EXISTENTE = "Já existe um produto com o mesmo nome.";

    private static final String PRODUTO_INEXISTENTE = "Não existe um produto com o código %d.";

    private static final String ERRO_MENSAGEM = "Ocorreu um erro ao tentar cadastrar um novo produto ";

    private static final String SKU_EXISTENTE = "Já existe um sku cadastrado no sistema.";

    private static final String CATEGORIA_PRODUTO_NAO_EXISTE = "Não existe cadastro de categoria com código %d";

    private static final String NAO_PERMITIDO_SALDO_ESTOQUE_NEGATIVO = "Informe um saldo para quantidade em estoque, pois não pode ser negativo";

    public Produto cadastrar(Produto produto) {
        try {

            Long categoriaId = produto.getCategoria().getId();

            Optional<Categoria> categoria = categoriaRepository.findById(categoriaId);

            if (!categoria.isPresent()) {
                throw new BussinesException(
                        String.format(CATEGORIA_PRODUTO_NAO_EXISTE, categoriaId));
            }
            List<Produto> produtosComMesmoNomeOuSkuCadastrado = new ArrayList<>();

            produtosComMesmoNomeOuSkuCadastrado.addAll(produtoRepository.existeProdutoNomeCadastrado(produto.getNome()));
            produtosComMesmoNomeOuSkuCadastrado.addAll(produtoRepository.existeSkuProdutoCadastrado(produto.getSku()));

            for (Produto produtoExistente : produtosComMesmoNomeOuSkuCadastrado) {
                if (produtoExistente.equals(produto) && produtoExistente.getNome().equals(produto.getNome())) {
                    throw new BussinesException(PRODUTO_EXISTENTE);
                }

                if (produtoExistente.getSku().equals(produto.getSku()) && produtoExistente.getSku().equals(produto.getSku())) {
                    throw new BussinesException(SKU_EXISTENTE);

                }
            }

            if (produto.getQuantidadeEstoque() < 0) {
                throw new IllegalArgumentException(NAO_PERMITIDO_SALDO_ESTOQUE_NEGATIVO);
            }

            calcularPrecoVenda(produto.getValorCusto(), produto.getQuantidadeEstoque(), produto);
            calcularIcmsValorSobreProduto(produto.getIcms());

            produtoRepository.save(produto);

        } catch (BussinesException | IllegalArgumentException e) {
            throw new BussinesException(ERRO_MENSAGEM + e.getMessage());
        }

        return produto;
    }


    public void calcularPrecoVenda(BigDecimal precoCusto, Integer quantidadeProdutos, Produto produto) {
        BigDecimal valorVenda = BigDecimal.valueOf(quantidadeProdutos).multiply(precoCusto);
        produto.setValorVenda(valorVenda);
    }

    public BigDecimal calcularIcmsValorSobreProduto(BigDecimal valorTotalIcms) {
        BigDecimal valor = valorTotalIcms;
        valor = valor.multiply(dividePorCem(aliquotaTotal(valorTotalIcms)));

        return arredondar(valor);
    }

    private BigDecimal dividePorCem(BigDecimal valor) {
        return valor.divide(new BigDecimal("100"), RoundingMode.HALF_UP);
    }

    private BigDecimal arredondar(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_DOWN);
    }

    private BigDecimal aliquotaTotal(BigDecimal aliquata) {
        BigDecimal porcentagemICMS = new BigDecimal("0.10");
        return aliquata.multiply(porcentagemICMS);
    }

    @Transactional
    public void reiniciarSequenciaProduto() {
        String sequenceName = "seq_produto";

        entityManager.createNativeQuery("SELECT setval(:sequenceName, COALESCE((SELECT MAX(id) + 1 FROM produto), 1), false)")
                .setParameter("sequenceName", sequenceName)
                .executeUpdate();
    }

    public Produto buscarPor(Long produtoId) {
        Produto restaurante = produtoRepository.findById(produtoId).get();
        return restaurante;
    }

}
