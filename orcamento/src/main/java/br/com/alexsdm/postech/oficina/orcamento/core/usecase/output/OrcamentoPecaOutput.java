package br.com.alexsdm.postech.oficina.orcamento.core.usecase.output;

import java.math.BigDecimal;
import java.util.UUID;

public record OrcamentoPecaOutput(UUID id,
                                  String nome,
                                  String descricao,
                                  Integer qtd,
                                  BigDecimal valorUnitario) {
}