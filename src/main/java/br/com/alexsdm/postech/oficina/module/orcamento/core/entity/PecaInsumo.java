package br.com.alexsdm.postech.oficina.module.orcamento.core.entity;

import java.math.BigDecimal;
import java.util.UUID;

public record PecaInsumo(
        UUID id,
        String nome,
        String descricao,
        BigDecimal precoVenda
) {
}
