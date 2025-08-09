package br.com.alexsdm.postech.oficina.admin.cliente.service.application.output;

public record BuscarClienteVeiculoOutput(String placa,
                                         String marca,
                                         String ano,
                                         String modelo,
                                         String cor) {
}
