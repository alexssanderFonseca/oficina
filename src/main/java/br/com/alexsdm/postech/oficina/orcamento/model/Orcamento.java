package br.com.alexsdm.postech.oficina.orcamento.model;

import br.com.alexsdm.postech.oficina.clientes.model.Cliente;
import br.com.alexsdm.postech.oficina.pecas.model.Peca;
import br.com.alexsdm.postech.oficina.servicos.model.Servico;

import java.math.BigDecimal;
import java.util.List;

public class Orcamento {
    private Cliente cliente;
    private List<Peca> pecas;
    private List<Servico> servicos;
    private OrcamentoStatus status;

    public Orcamento(Cliente cliente,
                     List<Peca> pecas,
                     List<Servico> servicos,
                     OrcamentoStatus status) {
        this.cliente = cliente;
        this.pecas = pecas;
        this.servicos = servicos;
        this.status = status;
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
        return this.pecas
                .stream()
                .map(Peca::getPrecoVenda)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
