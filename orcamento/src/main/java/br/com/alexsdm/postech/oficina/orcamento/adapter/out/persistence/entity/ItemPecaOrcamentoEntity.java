package br.com.alexsdm.postech.oficina.orcamento.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "item_peca_orcamento")
public class ItemPecaOrcamentoEntity {

    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "orcamento_id", nullable = false)
    private OrcamentoEntity orcamento;

    @Column(name = "peca_id", nullable = false)
    private UUID pecaId;

    private String nome;

    private Integer quantidade;

    private BigDecimal preco;

    private String descricao;



}