package br.com.alexsdm.postech.oficina.orcamento.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPecaOrcamento {

    private Long id;
    private PecaOrcamento peca;
    private Integer quantidade;

    public ItemPecaOrcamento(PecaOrcamento peca, Integer quantidade) {
        this.peca = peca;
        this.quantidade = quantidade;
    }

    public BigDecimal getTotal() {
        return peca.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }
}
