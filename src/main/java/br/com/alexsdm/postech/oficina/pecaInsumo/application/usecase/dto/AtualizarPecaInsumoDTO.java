package br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.dto;

import java.math.BigDecimal;

public record AtualizarPecaInsumoDTO(
    Long id,
    Integer quantidadeEstoque,
    BigDecimal precoCusto,
    BigDecimal precoVenda,
    Boolean ativo
) {}
