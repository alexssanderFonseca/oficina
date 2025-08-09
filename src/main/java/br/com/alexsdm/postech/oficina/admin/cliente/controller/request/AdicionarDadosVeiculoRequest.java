package br.com.alexsdm.postech.oficina.admin.cliente.controller.request;

public record AdicionarDadosVeiculoRequest(
        Long veiculoModeloId,
        String placa,
        String cor,
        String ano){

}
