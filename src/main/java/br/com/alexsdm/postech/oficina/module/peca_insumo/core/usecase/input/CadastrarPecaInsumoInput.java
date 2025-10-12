package br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.input;

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
