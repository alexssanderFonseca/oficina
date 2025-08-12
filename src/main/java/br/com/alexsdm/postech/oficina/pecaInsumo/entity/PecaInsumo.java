package br.com.alexsdm.postech.oficina.pecaInsumo.entity;


import br.com.alexsdm.postech.oficina.veiculomodelo.model.VeiculoModelo;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class PecaInsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private String codigoFabricante;


    private String marca;

    @ManyToMany
    @JoinTable(
            name = "peca_modelo_veiculo",
            joinColumns = @JoinColumn(name = "peca_id"),
            inverseJoinColumns = @JoinColumn(name = "modelo_id")
    )
    private List<VeiculoModelo> modelosCompativeis;

    private Integer quantidadeEstoque;

    private BigDecimal precoCusto;

    private BigDecimal precoVenda;

    private String categoria;

    private Boolean ativo;

    private LocalDateTime dataCadastro;

    private LocalDateTime dataAtualizacao;


    public PecaInsumo() {
    }

    public PecaInsumo(String nome,
                      String descricao,
                      String codigoFabricante,
                      String marca,
                      List<VeiculoModelo> modelosCompativeis,
                      Integer quantidadeEstoque,
                      BigDecimal precoCusto,
                      BigDecimal precoVenda,
                      String categoria,
                      Boolean ativo,
                      LocalDateTime dataCadastro,
                      LocalDateTime dataAtualizacao) {
        this.nome = nome;
        this.descricao = descricao;
        this.codigoFabricante = codigoFabricante;
        this.marca = marca;
        this.modelosCompativeis = modelosCompativeis;
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
