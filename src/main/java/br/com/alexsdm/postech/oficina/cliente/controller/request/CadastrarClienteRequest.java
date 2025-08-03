package br.com.alexsdm.postech.oficina.cliente.controller.request;

public record CadastrarClienteRequest(String nome,
                                      String sobrenome,
                                      String cpfCnpj,
                                      String email,
                                      String telefone,
                                      EnderecoRequest endereco,
                                      VeiculoRequest veiculo) {

}
