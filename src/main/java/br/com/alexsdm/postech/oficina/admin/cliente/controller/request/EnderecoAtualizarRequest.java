package br.com.alexsdm.postech.oficina.admin.cliente.controller.request;

public record EnderecoAtualizarRequest(String rua,
                                       String numero,
                                       String bairro,
                                       String cep,
                                       String cidade,
                                       String uf) {

}