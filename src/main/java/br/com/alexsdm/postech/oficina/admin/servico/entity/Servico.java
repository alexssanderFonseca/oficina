package br.com.alexsdm.postech.oficina.admin.servico.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer duracaoEstimada;
    private String categoria;
    private Boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;

    public Servico() {
    }

    public Servico(String nome,
                   String descricao,
                   BigDecimal preco,
                   Integer duracaoEstimada,
                   String categoria,
                   Boolean ativo,
                   LocalDateTime dataCadastro,
                   LocalDateTime dataAtualizacao) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoEstimada = duracaoEstimada;
        this.categoria = categoria;
        this.ativo = ativo;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
    }

    public void atualizar(BigDecimal preco,
                          boolean ativo) {
        this.preco = preco;
        this.ativo = ativo;
        this.dataAtualizacao = LocalDateTime.now();
    }

}
