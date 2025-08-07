package br.com.alexsdm.postech.oficina.orcamento.controller.request;

import br.com.alexsdm.postech.oficina.orcamento.service.input.PecaOrcamentoInput;

import java.util.List;
import java.util.UUID;

public record CriacaoOrcamentoRequest(
        String cpfCnpjCliente,
        UUID veiculoId,
        List<PecaOrcamentoInput> pecas,
        List<Long> servicos
) {
}
