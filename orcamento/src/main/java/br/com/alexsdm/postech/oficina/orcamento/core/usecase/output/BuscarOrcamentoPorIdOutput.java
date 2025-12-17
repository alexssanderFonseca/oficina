package br.com.alexsdm.postech.oficina.orcamento.core.usecase.output;

import br.com.alexsdm.postech.oficina.orcamento.core.entity.Cliente;
import br.com.alexsdm.postech.oficina.orcamento.core.entity.Orcamento;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record BuscarOrcamentoPorIdOutput(
        UUID id,
        BigDecimal valorTotal,
        BigDecimal valorTotalEmPecas,
        BigDecimal valorTotalEmServicos,
        String status,
        OrcamentoClienteOutput cliente,
        OrcamentoVeiculoOutput veiculo,
        List<OrcamentoPecaOutput> pecas,
        List<OrcamentoServicoOutput> servicos
) {

    public static BuscarOrcamentoPorIdOutput toOutput(Orcamento orcamento,
                                                      Cliente cliente) {
        var pecas = orcamento.getItensPeca().stream()
                .map(itemPecaOrcamento -> new OrcamentoPecaOutput(
                        itemPecaOrcamento.getPecaId(),
                        itemPecaOrcamento.getNome(),
                        itemPecaOrcamento.getDescricao(),
                        itemPecaOrcamento.getQuantidade(),
                        itemPecaOrcamento.getPreco()
                )).toList();

        var servicos = orcamento.getServicos().stream()
                .map(servico -> new OrcamentoServicoOutput(
                        servico.getServicoId(),
                        servico.getNome(),
                        servico.getDescricao(),
                        servico.getPreco()
                )).toList();

        var orcamentoClienteOutput = new OrcamentoClienteOutput(
                cliente.getId().toString(),
                cliente.getNome(),
                cliente.getSobrenome(),
                cliente.getCpfCnpj()
        );

        var orcamentoVeiculoOutput = new OrcamentoVeiculoOutput(
                cliente.getIdVeiculo(),
                cliente.getPlacaVeiculo(),
                cliente.getMarcaVeiculo(),
                cliente.getAnoVeiculo(),
                cliente.getModeloVeiculo()
        );

        return new BuscarOrcamentoPorIdOutput(
                orcamento.getId(),
                orcamento.getValorTotal(),
                orcamento.getValorTotalEmPecas(),
                orcamento.getValorTotalEmServicos(),
                orcamento.getStatus().name(),
                orcamentoClienteOutput,
                orcamentoVeiculoOutput,
                pecas,
                servicos
        );
    }
}
