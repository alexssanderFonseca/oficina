package br.com.alexsdm.postech.oficina.orcamento.adapter.in.controller.response;

public record OrcamentoClienteResponse(
        String nome,
        String sobrenome,
        String cpfCnpj
) {
}
