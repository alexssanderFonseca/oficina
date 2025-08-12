package br.com.alexsdm.postech.oficina.cliente.controller.request;


import jakarta.validation.constraints.NotBlank;

public record EnderecoRequest(@NotBlank String rua,
                              @NotBlank String numero,
                              @NotBlank String bairro,
                              @NotBlank String cidade,
                              @NotBlank String cep,
                              @NotBlank String uf) {
}