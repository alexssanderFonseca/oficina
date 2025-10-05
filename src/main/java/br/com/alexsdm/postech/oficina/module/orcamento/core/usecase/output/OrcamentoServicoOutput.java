package br.com.alexsdm.postech.oficina.module.orcamento.core.usecase.output;

import java.math.BigDecimal;
import java.util.UUID;

public record OrcamentoServicoOutput(
        UUID id,
        String nome,
        String descricao,
        BigDecimal valor
) {
}