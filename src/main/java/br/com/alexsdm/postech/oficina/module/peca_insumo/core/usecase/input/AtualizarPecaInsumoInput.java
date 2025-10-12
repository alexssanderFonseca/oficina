package br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.input;

import java.math.BigDecimal;
import java.util.UUID;

public record AtualizarPecaInsumoInput(
    UUID id,
    Integer quantidadeEstoque,
    BigDecimal precoCusto,
    BigDecimal precoVenda,
    Boolean ativo
) {}
