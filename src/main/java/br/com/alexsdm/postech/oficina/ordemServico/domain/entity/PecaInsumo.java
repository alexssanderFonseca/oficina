package br.com.alexsdm.postech.oficina.ordemServico.domain.entity;

import java.math.BigDecimal;

public record PecaInsumo(
        Long id,
        String nome,
        BigDecimal precoVenda
) {}
