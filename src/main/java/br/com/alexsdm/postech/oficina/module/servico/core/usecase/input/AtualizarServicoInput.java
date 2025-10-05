package br.com.alexsdm.postech.oficina.module.servico.core.usecase.input;

import java.math.BigDecimal;
import java.util.UUID;

public record AtualizarServicoInput(
        UUID id,
        BigDecimal preco,
        boolean ativo
) {
}
