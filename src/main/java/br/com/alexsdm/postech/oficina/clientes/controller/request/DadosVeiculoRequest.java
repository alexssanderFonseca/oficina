package br.com.alexsdm.postech.oficina.clientes.controller.request;

import java.util.UUID;

public record DadosVeiculoRequest(
        UUID veiculoModeloID,
        String placa,
        String cor,
        String ano,
        String modelo) {

}
