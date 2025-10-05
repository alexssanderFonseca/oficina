package br.com.alexsdm.postech.oficina.module.orcamento.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ItemServicoOrcamento {

    private UUID id;
    private UUID servicoId;
    private String nome;
    private BigDecimal preco;
    private String descricao;


    public ItemServicoOrcamento(
            UUID id,
            UUID servicoId,
            String nome,
            String descricao,
            BigDecimal preco) {
        this.id = id;
        this.servicoId = servicoId;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
    }
}
