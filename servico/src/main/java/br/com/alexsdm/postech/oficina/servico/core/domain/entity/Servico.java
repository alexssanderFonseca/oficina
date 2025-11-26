package br.com.alexsdm.postech.oficina.servico.core.domain.entity;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Servico {

    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer duracaoEstimada;
    private String categoria;
    private Boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;

    public Servico(UUID id,
                   String nome,
                   String descricao,
                   BigDecimal preco,
                   Integer duracaoEstimada,
                   String categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoEstimada = duracaoEstimada;
        this.categoria = categoria;
        this.ativo = true;
        this.dataCadastro = LocalDateTime.now();
    }

    public void atualizar(BigDecimal preco,
                          boolean ativo) {
        this.preco = preco;
        this.ativo = ativo;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void inativar() {
        this.ativo = false;
        this.dataAtualizacao = LocalDateTime.now();
    }
}
