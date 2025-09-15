package br.com.alexsdm.postech.oficina.orcamento.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity(name = "orcamento")
@Getter
@Setter
@NoArgsConstructor
public class OrcamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID clienteId;

    private UUID veiculoId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orcamento_id")
    private List<ItemPecaOrcamentoEntity> itensPeca;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orcamento_id")
    private List<ServicoOrcamentoEntity> servicos;

    @Enumerated(EnumType.STRING)
    private OrcamentoStatusEntity status;

    private BigDecimal valorTotal;

    private BigDecimal valorTotalEmServicos;

    private BigDecimal valorTotalEmPecas;
}
