package br.com.alexsdm.postech.oficina.cliente.service.input;

public record AdicionarVeiculoClientInput(
        Long veiculoModeloId,
        String placa,
        String ano,
        String cor
) {
}
