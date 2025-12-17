package br.com.alexsdm.postech.oficina.orcamento.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ItemPecaOrcamento {

    private UUID id;
    private UUID pecaId;
    private String nome;
    private Integer quantidade;
    private BigDecimal preco;
    private String descricao;

    public ItemPecaOrcamento(UUID id,
                             UUID pecaId,
                             String nome,
                             Integer quantidade,
                             BigDecimal preco,
                             String descricao) {
        this.id = id;
        this.pecaId = pecaId;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.descricao = descricao;
    }

    public BigDecimal getTotal() {
        return this.preco.multiply(BigDecimal.valueOf(quantidade));
    }
}
