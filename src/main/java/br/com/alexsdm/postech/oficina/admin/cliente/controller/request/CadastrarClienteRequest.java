package br.com.alexsdm.postech.oficina.admin.cliente.controller.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastrarClienteRequest(@NotBlank String nome,
                                      @NotBlank  String sobrenome,
                                      @NotBlank String cpfCnpj,
                                      @NotBlank @Email String email,
                                      @NotBlank String telefone,
                                      @NotNull EnderecoRequest endereco) {

}
