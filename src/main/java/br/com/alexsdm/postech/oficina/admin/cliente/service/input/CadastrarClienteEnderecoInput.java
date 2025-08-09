package br.com.alexsdm.postech.oficina.admin.cliente.service.input;

public record CadastrarClienteEnderecoInput(String rua,
                                            String bairro,
                                            String cidade,
                                            String cep,
                                            String uf) {
}