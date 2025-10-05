package br.com.alexsdm.postech.oficina.module.ordemServico.core.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public record Servico(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco
) {
}
