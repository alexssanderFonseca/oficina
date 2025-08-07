package br.com.alexsdm.postech.oficina.cliente.service.input;

public record CadastrarClienteEnderecoInput(String rua,
                                            String bairro,
                                            String cidade,
                                            String cep,
                                            String uf) {
}