package com.gerenciamento.produtos.service;

import com.gerenciamento.produtos.repository.CategoriaRepository;
import com.gerenciamento.produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;


}
