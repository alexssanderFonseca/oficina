package br.com.alexsdm.postech.oficina.servico.application.usecase.input;

import java.math.BigDecimal;

public record AtualizarServicoInput(
        Long id,
        BigDecimal preco,
        boolean ativo
) {
}
