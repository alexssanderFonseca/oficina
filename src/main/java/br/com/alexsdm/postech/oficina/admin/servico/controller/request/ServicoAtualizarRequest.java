package br.com.alexsdm.postech.oficina.admin.servico.controller.request;

import java.math.BigDecimal;

public record ServicoAtualizarRequest(BigDecimal preco,
                                      boolean ativo) {
}
