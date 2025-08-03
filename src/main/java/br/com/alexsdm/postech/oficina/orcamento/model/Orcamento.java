package br.com.alexsdm.postech.oficina.orcamento.model;

import br.com.alexsdm.postech.oficina.cliente.model.Cliente;
import br.com.alexsdm.postech.oficina.servico.model.Servico;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;


@Entity
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Cliente cliente;

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

    @Transient
    private BigDecimal valorTotal;

    @Transient
    private BigDecimal valorTotalEmServicos;

    @Transient
    private BigDecimal valorTotalEmPecas;

    public Orcamento() {
    }

    public Orcamento(Cliente cliente,
                     List<ItemPecaOrcamento> itensPeca,
                     List<Servico> servicos,
                     OrcamentoStatus status) {
        this.cliente = cliente;
        this.itensPeca = itensPeca;
        this.servicos = servicos;
        this.status = status;
        this.itensPeca.forEach(item -> item.setOrcamento(this));
        this.valorTotal = calcularValorTotal();
        this.valorTotalEmServicos = calcularValorTotalServicos();
        this.valorTotalEmPecas = calcularValorTotalPecas();
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

    public Long getId() {
        return this.id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPecaOrcamento> getItensPeca() {
        return itensPeca;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public OrcamentoStatus getStatus() {
        return status;
    }


    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public BigDecimal getValorTotalEmServicos() {
        return valorTotalEmServicos;
    }

    public BigDecimal getValorTotalEmPecas() {
        return valorTotalEmPecas;
    }
}

