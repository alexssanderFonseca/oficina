package br.com.alexsdm.postech.oficina.cliente.domain.entity;

import lombok.Builder;

import java.util.UUID;


@Builder
public record Endereco(UUID id,
                       String rua,
                       String numero,
                       String bairro,
                       String cep,
                       String cidade,
                       String uf) {
}