package br.com.alexsdm.postech.oficina.orcamento.domain.entity;

import java.math.BigDecimal;

public record Servico(
        Long id,
        String nome,
        BigDecimal preco
) {
}
