package br.com.alexsdm.postech.oficina.pecaInsumo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PecaInsumo {

    private Long id;
    private String nome;
    private String descricao;
    private String codigoFabricante;
    private String marca;
    private List<VeiculoModelo> modelosCompativeis;
    private Integer quantidadeEstoque;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private String categoria;
    private Boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;

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
