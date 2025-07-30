package br.com.alexsdm.postech.oficina.clientes.controller.request;

public record CadastrarClienteRequest(String nome,
                                      String sobrenome,
                                      String cpfCnpj,
                                      String email,
                                      String telefone,
                                      EnderecoRequest endereco,
                                      VeiculoRequest veiculo) {

}
