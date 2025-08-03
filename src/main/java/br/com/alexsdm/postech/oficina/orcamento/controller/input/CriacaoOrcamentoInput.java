package br.com.alexsdm.postech.oficina.orcamento.controller.input;

import br.com.alexsdm.postech.oficina.orcamento.service.input.PecaOrcamentoInput;

import java.util.List;

public record CriacaoOrcamentoInput(
        String cpfCnpjCliente,
        List<PecaOrcamentoInput> pecas,
        List<Long> servicos
) {
}
