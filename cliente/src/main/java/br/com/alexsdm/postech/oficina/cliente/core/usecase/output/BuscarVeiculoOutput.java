package br.com.alexsdm.postech.oficina.cliente.core.usecase.output;

public record BuscarVeiculoOutput(String id,
                                  String marca,
                                  String ano,
                                  String modelo,
                                  String cor) {
}
