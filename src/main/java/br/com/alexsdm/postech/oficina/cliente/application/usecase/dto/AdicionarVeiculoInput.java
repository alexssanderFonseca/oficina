package br.com.alexsdm.postech.oficina.cliente.application.usecase.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AdicionarVeiculoInput(UUID clienteId,
                                    Long veiculoModeloId,
                                    String placa,
                                    String ano,
                                    String cor) {
}
