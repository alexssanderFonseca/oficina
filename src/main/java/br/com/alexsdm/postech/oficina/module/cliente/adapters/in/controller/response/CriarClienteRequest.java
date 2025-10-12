package br.com.alexsdm.postech.oficina.module.cliente.adapters.in.controller.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CriarClienteRequest(
        @NotBlank String nome,
        @NotBlank String sobrenome,
        @NotBlank String cpfCnpj,
        @NotBlank String email,
        @NotBlank String telefone,
        @Valid EnderecoRequest endereco
) {
    public record EnderecoRequest(
            @NotBlank String rua,
            @NotBlank String numero,
            @NotBlank String bairro,
            @NotBlank String cep,
            @NotBlank String cidade,
            @NotBlank String uf
    ) {}
}
