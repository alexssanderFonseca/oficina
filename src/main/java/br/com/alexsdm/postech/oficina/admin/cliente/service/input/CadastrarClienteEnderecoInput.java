package br.com.alexsdm.postech.oficina.admin.cliente.service.input;

public record CadastrarClienteEnderecoInput(String rua,
                                            String numero,
                                            String bairro,
                                            String cidade,
                                            String cep,
                                            String uf) {
}