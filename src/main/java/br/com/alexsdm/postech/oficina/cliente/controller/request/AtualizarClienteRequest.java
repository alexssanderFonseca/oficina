package br.com.alexsdm.postech.oficina.cliente.controller.request;


public record AtualizarClienteRequest(String email,
                                      String telefone,
                                      EnderecoAtualizarRequest endereco) {
}

