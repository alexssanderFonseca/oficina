package br.com.alexsdm.postech.oficina.orcamento.service.output;

import java.math.BigDecimal;

public record OrcamentoPecaOutput(String nome,
                                  String descricao,
                                  Integer qtd,
                                  BigDecimal valorUnitario) {
}
