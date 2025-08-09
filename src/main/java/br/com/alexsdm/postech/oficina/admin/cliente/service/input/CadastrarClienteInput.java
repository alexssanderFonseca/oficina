package br.com.alexsdm.postech.oficina.admin.cliente.service.input;

public record CadastrarClienteInput(String nome,
                                    String sobrenome,
                                    String cpfCnpj,
                                    String email,
                                    String telefone,
                                    CadastrarClienteEnderecoInput endereco) {

}
