package br.com.alexsdm.postech.oficina.module.orcamento.adapter.in.controller.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrcamentoResponse(
        UUID id,
        BigDecimal valorTotal,
        BigDecimal valorTotalEmPecas,
        BigDecimal valorTotalEmServicos,
        OrcamentoClienteResponse cliente,
        OrcamentoVeiculoResponse veiculo,
        List<OrcamentoPecaResponse> pecas,
        List<OrcamentoServicoResponse> servicos,
        String status
) {
}
