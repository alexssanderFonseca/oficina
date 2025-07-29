package br.com.alexsdm.postech.oficina.clientes.controller.request;

public record CadastrarClienteRequest(String nome,
                                      String sobrenome,
                                      String cpf,
                                      String email,
                                      String telefone,
                                      EnderecoRequest endereco,
                                      VeiculoRequest veiculo) {

}
