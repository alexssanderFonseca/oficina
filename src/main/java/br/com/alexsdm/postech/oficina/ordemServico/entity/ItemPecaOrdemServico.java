package br.com.alexsdm.postech.oficina.ordemServico.entity;

import br.com.alexsdm.postech.oficina.pecaInsumo.entity.PecaInsumo;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
public class ItemPecaOrdemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PecaInsumo peca;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    private OrdemServico ordemServico;

    private BigDecimal precoUnitario;

    private Integer quantidade;

    public ItemPecaOrdemServico() {
    }

    public ItemPecaOrdemServico(PecaInsumo peca,
                                BigDecimal precoUnitario,
                                Integer quantidade,
                                OrdemServico ordemServico) {

        this.peca = peca;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.ordemServico = ordemServico;
    }


}
