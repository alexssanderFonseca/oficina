package br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.input;

import java.math.BigDecimal;

public record CadastrarPecaInsumoInput(
    String nome,
    String descricao,
    String codigoFabricante,
    String marca,
    Integer quantidadeEstoque,
    BigDecimal precoCusto,
    BigDecimal precoVenda,
    String categoria
) {}
