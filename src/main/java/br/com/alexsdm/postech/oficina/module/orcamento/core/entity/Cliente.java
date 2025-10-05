package br.com.alexsdm.postech.oficina.module.orcamento.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Cliente {
    private UUID id;
    private String nome;
    private String sobrenome;
    private String cpfCnpj;
    private String idVeiculo;
    private String placaVeiculo;
    private String marcaVeiculo;
    private String anoVeiculo;
    private String modeloVeiculo;
}
