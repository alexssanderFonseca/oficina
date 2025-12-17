package br.com.alexsdm.postech.oficina.orcamento.core.usecase.output;

public record OrcamentoClienteOutput(
        String id,
        String nome,
        String sobrenome,
        String cpfCnpj
) {
}