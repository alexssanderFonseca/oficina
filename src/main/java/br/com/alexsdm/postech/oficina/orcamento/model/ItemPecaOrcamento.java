package br.com.alexsdm.postech.oficina.orcamento.model;

import br.com.alexsdm.postech.oficina.peca.model.Peca;
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
    private Peca peca;

    private Integer quantidade;

    public ItemPecaOrcamento() {
    }

    public ItemPecaOrcamento(Peca peca, Integer quantidade) {
        this.peca = peca;
        this.quantidade = quantidade;
    }

    public BigDecimal getTotal() {
        return peca.getPrecoVenda().multiply(BigDecimal.valueOf(quantidade));
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }
}
