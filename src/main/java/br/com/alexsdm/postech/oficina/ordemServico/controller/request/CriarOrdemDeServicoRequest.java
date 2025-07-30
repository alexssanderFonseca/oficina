package br.com.alexsdm.postech.oficina.ordemServico.controller.request;

public record CriarOrdemDeServicoRequest(
        String cpfCnpj,
        String placaVeiculo,
        String orcamentoId
) {
}
