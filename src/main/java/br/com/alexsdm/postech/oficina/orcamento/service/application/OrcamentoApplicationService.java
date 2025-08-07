package br.com.alexsdm.postech.oficina.orcamento.service.application;


import br.com.alexsdm.postech.oficina.cliente.service.domain.ClienteDomainService;
import br.com.alexsdm.postech.oficina.orcamento.service.domain.OrcamentoDomainService;
import br.com.alexsdm.postech.oficina.orcamento.service.output.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrcamentoApplicationService {

    private final OrcamentoDomainService orcamentoDomainService;
    private final ClienteDomainService clienteService;


    public OrcamentoOutput buscarPorId(Long id) {

        var orcamento = orcamentoDomainService.buscarPorId(id)
                .orElseThrow(RuntimeException::new);

        var cliente = clienteService.buscar(orcamento.getClienteId());
        var veiculo = cliente.getVeiculoPorId(orcamento.getVeiculoId()).get();
        var orcamentoVeiculoResponse = new OrcamentoVeiculoResponse(
                veiculo.getPlaca(),
                veiculo.getVeiculoModelo().getMarca(),
                veiculo.getAno(),
                veiculo.getVeiculoModelo().getModelo()
        );

        var pecasOutput = orcamento.getItensPeca()
                .stream()
                .map(itemPeca -> new OrcamentoPecaOutput(
                        itemPeca.getPeca().getNome(),
                        itemPeca.getPeca().getDescricao(),
                        itemPeca.getQuantidade(),
                        itemPeca.getPeca().getPrecoVenda())
                ).toList();

        var servicosOuput = orcamento.getServicos()
                .stream()
                .map(servico -> new OrcamentoServicoOutput(
                        servico.getNome(),
                        servico.getDescricao(),
                        servico.getPreco())
                ).toList();


        var clienteOutput = new OrcamentoClienteOutput(cliente.getNome(),
                cliente.getSobrenome(),
                cliente.getCpfCnpj());

        return new OrcamentoOutput(
                orcamento.getId(),
                orcamento.getValorTotal(),
                orcamento.getValorTotalEmPecas(),
                orcamento.getValorTotalEmServicos(),
                orcamento.getStatus().name(),
                clienteOutput,
                orcamentoVeiculoResponse,
                pecasOutput,
                servicosOuput
        );
    }
}
