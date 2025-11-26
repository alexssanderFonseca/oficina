package br.com.alexsdm.postech.oficina.orcamento.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "orcamento_servico")
@Getter
@Setter
@NoArgsConstructor
public class ItemServicoOrcamentoEntity {

    @Id
    private UUID id;
    private UUID servicoId;
    private String nome;
    private BigDecimal preco;
    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "orcamento_id", nullable = false)
    private OrcamentoEntity orcamento;
}
