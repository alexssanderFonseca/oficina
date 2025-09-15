package br.com.alexsdm.postech.oficina.orcamento.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "item_peca_orcamento")
@Getter
@Setter
@NoArgsConstructor
public class ItemPecaOrcamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "peca_orcamento_id")
    private PecaOrcamentoEntity peca;

    private Integer quantidade;
}
