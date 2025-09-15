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
public class PecaOrcamento {

    private Long id;
    private Long pecaId;
    private String nome;
    private BigDecimal preco;

    public PecaOrcamento(Long pecaId, String nome, BigDecimal preco) {
        this.pecaId = pecaId;
        this.nome = nome;
        this.preco = preco;
    }
}
