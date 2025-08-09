package br.com.alexsdm.postech.oficina.admin.cliente.service.input;

public record AdicionarVeiculoClientInput(
        Long veiculoModeloId,
        String placa,
        String ano,
        String cor
) {
}
