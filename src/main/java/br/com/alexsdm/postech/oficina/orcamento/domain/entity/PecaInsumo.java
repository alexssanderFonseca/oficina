package br.com.alexsdm.postech.oficina.orcamento.domain.entity;

import java.math.BigDecimal;

public record PecaInsumo(
        Long id,
        String nome,
        BigDecimal precoVenda
) {
}
