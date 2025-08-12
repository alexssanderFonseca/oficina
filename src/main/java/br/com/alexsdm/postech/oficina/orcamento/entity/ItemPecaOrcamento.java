package br.com.alexsdm.postech.oficina.orcamento.entity;

import br.com.alexsdm.postech.oficina.pecaInsumo.entity.PecaInsumo;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ItemPecaOrcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "orcamento_id", nullable = false)
    private Orcamento orcamento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "peca_id")
    private PecaInsumo peca;

    private Integer quantidade;

    public ItemPecaOrcamento() {
    }

    public ItemPecaOrcamento(PecaInsumo peca, Integer quantidade) {
        this.peca = peca;
        this.quantidade = quantidade;
    }

    public BigDecimal getTotal() {
        return peca.getPrecoVenda().multiply(BigDecimal.valueOf(quantidade));
    }

    public int getQuantidade() {
        return quantidade;
    }

    public PecaInsumo getPeca() {
        return peca;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }
}
