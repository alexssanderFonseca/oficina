package br.com.alexsdm.postech.oficina.orcamento.controller.response;

public record OrcamentoClienteResponse(
        String nome,
        String sobrenome,
        String cpfCnpj
) {
}
