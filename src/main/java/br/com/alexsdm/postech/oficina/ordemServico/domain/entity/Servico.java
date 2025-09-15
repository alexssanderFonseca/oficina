package br.com.alexsdm.postech.oficina.ordemServico.domain.entity;

import java.math.BigDecimal;

public record Servico(
        Long id,
        String nome,
        BigDecimal preco
) {}
