package br.com.alexsdm.postech.oficina.orcamento.infrastructure.controller.response;

public record OrcamentoClienteResponse(
        String nome,
        String sobrenome,
        String cpfCnpj
) {
}
