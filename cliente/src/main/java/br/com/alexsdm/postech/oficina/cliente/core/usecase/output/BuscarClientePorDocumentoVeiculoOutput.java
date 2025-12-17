package br.com.alexsdm.postech.oficina.cliente.core.usecase.output;

import lombok.Builder;

@Builder
public record BuscarClientePorDocumentoVeiculoOutput(
        String id,
        String placa,
        String marca,
        String modelo,
        String cor,
        String ano
) {
}
