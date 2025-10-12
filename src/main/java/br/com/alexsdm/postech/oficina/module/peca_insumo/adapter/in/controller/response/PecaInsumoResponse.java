package br.com.alexsdm.postech.oficina.module.peca_insumo.adapter.in.controller.response;

import java.math.BigDecimal;
import java.util.UUID;

public record PecaInsumoResponse(UUID id,
                                 String nome,
                                 BigDecimal precoVenda,
                                 Integer quantidadeEstoque) {
}
