package br.com.alexsdm.postech.oficina.module.orcamento.adapter.in.controller.response;

public record OrcamentoClienteResponse(
        String nome,
        String sobrenome,
        String cpfCnpj
) {
}
