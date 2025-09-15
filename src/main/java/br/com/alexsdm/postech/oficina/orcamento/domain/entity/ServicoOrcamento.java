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
public class ServicoOrcamento {

    private Long id;
    private Long servicoId;
    private String nome;
    private BigDecimal preco;

    public ServicoOrcamento(Long servicoId, String nome, BigDecimal preco) {
        this.servicoId = servicoId;
        this.nome = nome;
        this.preco = preco;
    }
}
