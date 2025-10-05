package br.com.alexsdm.postech.oficina.module.pecaInsumo.core.domain.entity;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter

public class PecaInsumo {

    private UUID id;
    private String nome;
    private String descricao;
    private String codigoFabricante;
    private String marca;
    private Integer quantidadeEstoque;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private String categoria;
    private Boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;

    public PecaInsumo(UUID id,
                      String nome,
                      String descricao,
                      String codigoFabricante,
                      String marca,
                      Integer quantidadeEstoque,
                      BigDecimal precoCusto,
                      BigDecimal precoVenda,
                      String categoria,
                      Boolean ativo,
                      LocalDateTime dataCadastro,
                      LocalDateTime dataAtualizacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.codigoFabricante = codigoFabricante;
        this.marca = marca;
        this.quantidadeEstoque = quantidadeEstoque;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.categoria = categoria;
        this.ativo = ativo;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
    }

    public boolean isDisponivel() {
        return quantidadeEstoque != null && quantidadeEstoque > 0;
    }

    public void retirar(Integer qtd) {
        this.quantidadeEstoque -= qtd;
    }

    public void atualizarQuantidadeEstoque(Integer qtd) {
        if (qtd != null) this.quantidadeEstoque = qtd;
    }

    public void atualizarStatus(Boolean ativo) {
        if (ativo != null) this.ativo = ativo;
    }

    public void atualizarPrecos(BigDecimal precoCusto, BigDecimal precoVenda) {
        if (precoCusto != null) this.precoCusto = precoCusto;
        if (precoVenda != null) this.precoVenda = precoVenda;
    }
}
