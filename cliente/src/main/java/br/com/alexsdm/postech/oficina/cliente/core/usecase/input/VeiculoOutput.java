package br.com.alexsdm.postech.oficina.cliente.core.usecase.input;

import lombok.Builder;

@Builder
public record VeiculoOutput(String id,
                            String placa,
                            String marca,
                            String modelo,
                            String cor,
                            String ano) {
}
