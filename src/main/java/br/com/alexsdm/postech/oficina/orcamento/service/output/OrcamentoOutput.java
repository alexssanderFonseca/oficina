package br.com.alexsdm.postech.oficina.orcamento.service.output;

import java.math.BigDecimal;
import java.util.List;

public record OrcamentoOutput(
        Long id,
        BigDecimal valorTotal,
        BigDecimal valorTotalEmPecas,
        BigDecimal valorTotalEmServicos,
        String status,
        OrcamentoClienteOutput cliente,
        OrcamentoVeiculoResponse veiculoResponse,
        List<OrcamentoPecaOutput> pecas,
        List<OrcamentoServicoOutput> servicos
) {
}
