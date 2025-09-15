package br.com.alexsdm.postech.oficina.orcamento.infrastructure.controller.response;

public record OrcamentoServicoResponse(
        String nome,
        String descricao,
        String valor
) {
}
