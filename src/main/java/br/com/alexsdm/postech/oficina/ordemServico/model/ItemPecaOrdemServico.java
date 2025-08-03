package br.com.alexsdm.postech.oficina.ordemServico.model;

import br.com.alexsdm.postech.oficina.peca.model.Peca;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ItemPecaOrdemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Peca peca;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    private OrdemServico ordemServico;

    private Integer quantidade;

    public ItemPecaOrdemServico() {
    }

    public ItemPecaOrdemServico(Peca peca,
                                Integer quantidade,
                                OrdemServico ordemServico) {

        this.peca = peca;
        this.quantidade = quantidade;
        this.ordemServico = ordemServico;
    }


    public Long getId() {
        return id;
    }

    public Peca getPeca() {
        return peca;
    }

    public Integer getQuantidade() {
        return quantidade;
    }


}
