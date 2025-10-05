package br.com.alexsdm.postech.oficina.module.ordemServico.core.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public record PecaInsumo(
        UUID id,
        String nome,
        String descricao,
        Integer quantidade,
        BigDecimal precoVenda
) {
}
