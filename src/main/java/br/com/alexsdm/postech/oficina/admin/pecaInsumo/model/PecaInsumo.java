package br.com.alexsdm.postech.oficina.admin.pecaInsumo.model;


import br.com.alexsdm.postech.oficina.admin.veiculo.model.VeiculoModelo;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
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


    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCodigoFabricante() {
        return codigoFabricante;
    }


    public String getMarca() {
        return marca;
    }

    public List<VeiculoModelo> getModelosCompativeis() {
        return modelosCompativeis;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public BigDecimal getPrecoCusto() {
        return precoCusto;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public String getCategoria() {
        return categoria;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
}
