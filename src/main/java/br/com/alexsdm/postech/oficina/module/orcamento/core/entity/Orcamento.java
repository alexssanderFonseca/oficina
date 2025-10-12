package br.com.alexsdm.postech.oficina.module.orcamento.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Orcamento {

    private UUID id;
    private UUID clienteId;
    private UUID veiculoId;
    private List<ItemPecaOrcamento> itensPeca;
    private List<ItemServicoOrcamento> servicos;
    private OrcamentoStatus status;
    private BigDecimal valorTotal;
    private BigDecimal valorTotalEmServicos;
    private BigDecimal valorTotalEmPecas;

    public Orcamento(
            UUID id,
            UUID clienteId,
            UUID veiculoId,
            List<ItemPecaOrcamento> itensPeca,
            List<ItemServicoOrcamento> servicos) {
        this.id = id;
        this.clienteId = clienteId;
        this.veiculoId = veiculoId;
        this.itensPeca = itensPeca;
        this.servicos = servicos;
        this.status = OrcamentoStatus.CRIADO;
        this.valorTotalEmServicos = calcularValorTotalServicos();
        this.valorTotalEmPecas = calcularValorTotalPecas();
        this.valorTotal = calcularValorTotal();
    }

    public BigDecimal calcularValorTotal() {
        return calcularValorTotalPecas().add(calcularValorTotalServicos());
    }

    private BigDecimal calcularValorTotalServicos() {
        if (this.servicos == null) {
            return BigDecimal.ZERO;
        }
        return this.servicos.stream()
                .map(ItemServicoOrcamento::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calcularValorTotalPecas() {
        if (this.itensPeca == null) {
            return BigDecimal.ZERO;
        }
        return this.itensPeca.stream()
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

