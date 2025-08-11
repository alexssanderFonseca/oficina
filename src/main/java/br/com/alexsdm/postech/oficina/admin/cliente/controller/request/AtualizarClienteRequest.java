package br.com.alexsdm.postech.oficina.admin.cliente.controller.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizarClienteRequest(@NotBlank String email,
                                      @NotBlank String telefone,
                                      @NotNull EnderecoAtualizarRequest endereco) {
}

