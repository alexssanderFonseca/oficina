package br.com.alexsdm.postech.oficina.orcamento.core.usecase.output;

public record OrcamentoVeiculoOutput(String id,
                                     String placa,
                                     String marca,
                                     String ano,
                                     String modelo) {
}