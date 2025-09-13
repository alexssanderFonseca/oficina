package br.com.alexsdm.postech.oficina.cliente.application.usecase.dto;

import lombok.Builder;

@Builder
public record VeiculoOutput(String placa,
                            String marca,
                            String modelo,
                            String cor,
                            String ano) {
}
