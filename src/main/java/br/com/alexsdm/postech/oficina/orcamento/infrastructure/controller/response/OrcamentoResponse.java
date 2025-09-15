package br.com.alexsdm.postech.oficina.orcamento.infrastructure.controller.response;

import java.math.BigDecimal;
import java.util.List;

public record OrcamentoResponse(
        Long id,
        BigDecimal valorTotal,
        BigDecimal valorTotalEmPecas,
        BigDecimal valorTotalEmServicos,
        OrcamentoClienteResponse cliente,
        OrcamentoVeiculoResponse veiculoResponse,
        List<OrcamentoPecaResponse> pecas,
        List<OrcamentoServicoResponse> servicos
) {
}
