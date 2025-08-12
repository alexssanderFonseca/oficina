package br.com.alexsdm.postech.oficina.cliente.service.application.output;

public record BuscarClienteEnderecoOutput(String rua,
                                          String bairro,
                                          String cep,
                                          String cidade,
                                          String uf) {
}
