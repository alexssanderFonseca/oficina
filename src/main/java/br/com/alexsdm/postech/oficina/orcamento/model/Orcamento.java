package br.com.alexsdm.postech.oficina.orcamento.model;

import br.com.alexsdm.postech.oficina.servico.model.Servico;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID clienteId;

    private UUID veiculoId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orcamento_id")
    private List<ItemPecaOrcamento> itensPeca;

    @ManyToMany
    @JoinTable(
            name = "orcamento_servico",
            joinColumns = @JoinColumn(name = "orcamento_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private List<Servico> servicos;

    private OrcamentoStatus status;

    private BigDecimal valorTotal;


    private BigDecimal valorTotalEmServicos;


    private BigDecimal valorTotalEmPecas;

    public Orcamento() {
    }

    public Orcamento(UUID clienteId,
                     UUID veiculoId,
                     List<ItemPecaOrcamento> itensPeca,
                     List<Servico> servicos,
                     OrcamentoStatus status) {
        this.clienteId = clienteId;
        this.itensPeca = itensPeca;
        this.servicos = servicos;
        this.status = status;
        this.itensPeca.forEach(item -> item.setOrcamento(this));
        this.valorTotal = calcularValorTotal();
        this.valorTotalEmServicos = calcularValorTotalServicos();
        this.valorTotalEmPecas = calcularValorTotalPecas();
        this.veiculoId = veiculoId;
    }

    public BigDecimal calcularValorTotal() {
        return calcularValorTotalPecas()
                .add(calcularValorTotalServicos());
    }

    public BigDecimal calcularValorTotalServicos() {
        return this.servicos
                .stream()
                .map(Servico::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularValorTotalPecas() {
        return this.itensPeca
                .stream()
                .map(ItemPecaOrcamento::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void aceitar() {
        this.status = OrcamentoStatus.ACEITO;
    }

    public boolean isAceito() {
        return OrcamentoStatus.ACEITO.equals(this.status);
    }

    public void recusar() {
        this.status = OrcamentoStatus.RECUSADO;
    }
}

