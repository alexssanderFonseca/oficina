package br.com.alexsdm.postech.oficina.servico.application.usecase.input;

import java.math.BigDecimal;

public record CadastrarServicoInput(
        String nome,
        String descricao,
        BigDecimal preco,
        Integer duracaoEstimada,
        String categoria
) {
}
