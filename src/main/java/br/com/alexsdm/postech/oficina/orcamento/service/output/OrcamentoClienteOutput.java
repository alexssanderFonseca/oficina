package br.com.alexsdm.postech.oficina.orcamento.service.output;

public record OrcamentoClienteOutput(
        String nome,
        String sobrenome,
        String cpfCnpj
) {
}
