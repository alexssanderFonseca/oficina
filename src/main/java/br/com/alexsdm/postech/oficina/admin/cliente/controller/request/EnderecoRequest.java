package br.com.alexsdm.postech.oficina.admin.cliente.controller.request;

public record EnderecoRequest(String rua,
                       String bairro,
                       String cidade,
                       String cep,
                       String uf) {
}