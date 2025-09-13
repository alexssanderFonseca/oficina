package br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.request;


import jakarta.validation.constraints.NotBlank;

public record EnderecoAtualizarRequest(@NotBlank String rua,
                                       @NotBlank String numero,
                                       @NotBlank String bairro,
                                       @NotBlank String cep,
                                       @NotBlank String cidade,
                                       @NotBlank String uf) {

}