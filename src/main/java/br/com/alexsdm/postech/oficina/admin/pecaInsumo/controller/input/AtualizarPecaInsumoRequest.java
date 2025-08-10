package br.com.alexsdm.postech.oficina.admin.pecaInsumo.controller.input;

import java.math.BigDecimal;

public record AtualizarPecaInsumoRequest(Integer qtd,
                                         BigDecimal precoCusto,
                                         BigDecimal precoVenda,
                                         Boolean ativa) {
}
