package br.com.alexsdm.postech.oficina.ordemServico.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "item_peca_ordem_servico")
@Getter
@Setter
@NoArgsConstructor
public class ItemPecaOrdemServicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

            @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "peca_os_id", referencedColumnName = "id")
    private PecaOsEntity peca;

    private BigDecimal precoUnitario;
    private Integer quantidade;
}
