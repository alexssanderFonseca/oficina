package br.com.alexsdm.postech.oficina.orcamento.service.output;

import java.math.BigDecimal;

public record OrcamentoServicoOutput(
        String nome,
        String descricao,
        BigDecimal valor
) {
}
