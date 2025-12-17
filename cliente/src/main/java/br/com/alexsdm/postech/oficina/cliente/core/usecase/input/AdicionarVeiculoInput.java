package br.com.alexsdm.postech.oficina.cliente.core.usecase.input;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AdicionarVeiculoInput(UUID clienteId,
                                    String marca,
                                    String modelo,
                                    String placa,
                                    String ano,
                                    String cor) {
}
