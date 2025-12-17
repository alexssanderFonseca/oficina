package br.com.alexsdm.postech.oficina.orcamento.core.entity;

import java.math.BigDecimal;
import java.util.UUID;

public record Servico(
        UUID id,
        String nome,
        BigDecimal preco,
        String descricao
) {

}
