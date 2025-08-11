package br.com.alexsdm.postech.oficina.orcamento.service.application;


import br.com.alexsdm.postech.oficina.admin.cliente.entity.Cliente;
import br.com.alexsdm.postech.oficina.admin.cliente.entity.Veiculo;
import br.com.alexsdm.postech.oficina.admin.cliente.service.application.ClienteApplicationService;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.service.application.PecaInsumoApplicationService;
import br.com.alexsdm.postech.oficina.admin.servico.service.application.ServicoApplicationService;
import br.com.alexsdm.postech.oficina.orcamento.exception.OrcamentoException;
import br.com.alexsdm.postech.oficina.orcamento.exception.OrcamentoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.orcamento.model.ItemPecaOrcamento;
import br.com.alexsdm.postech.oficina.orcamento.model.Orcamento;
import br.com.alexsdm.postech.oficina.orcamento.model.OrcamentoStatus;
import br.com.alexsdm.postech.oficina.orcamento.repository.OrcamentoRepository;
import br.com.alexsdm.postech.oficina.orcamento.service.domain.OrcamentoDomainService;
import br.com.alexsdm.postech.oficina.orcamento.service.input.PecaOrcamentoInput;
import br.com.alexsdm.postech.oficina.orcamento.service.output.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrcamentoApplicationService {

    private final OrcamentoDomainService orcamentoDomainService;
    private final OrcamentoRepository orcamentoRepository;
    private final ClienteApplicationService clienteApplicationService;
    private final PecaInsumoApplicationService pecaApplicationService;
    private final ServicoApplicationService servicoApplicationService;
    private final OrcamentoPdfGeradorService orcamentoPdfGeradorService;

    public Long criar(String cpfCnpjCliente,
                      UUID veiculoId,
                      List<PecaOrcamentoInput> pecasOrcamentoInput,
                      List<Long> servicosId) {

        var cliente = clienteApplicationService.buscarPorCpfCnpj(cpfCnpjCliente)
                .orElseThrow(() -> new OrcamentoException("Não foi encontrado o cliente vinculado ao orçamento informado"));

        var itens = pecasOrcamentoInput.stream()
                .map(input -> new ItemPecaOrcamento(
                        pecaApplicationService.buscarPorId(input.pecaId()),
                        input.qtd()))
                .toList();

        var servicos = servicosId.stream()
                .map(servicoApplicationService::buscar)
                .toList();

        var orcamento = new Orcamento(cliente.getId(), veiculoId, itens, servicos, OrcamentoStatus.CRIADO);

        orcamentoRepository.save(orcamento);

        return orcamento.getId();
    }

    public byte[] enviar(Long orcamentoId) {
        var orcamento = orcamentoRepository.findById(orcamentoId)
                .orElseThrow(OrcamentoNaoEncontradaException::new);
        var cliente = buscarCliente(orcamento.getClienteId());
        var veiculo = buscarVeiculo(cliente, orcamento.getVeiculoId());
        return orcamentoPdfGeradorService.gerar(orcamento, cliente, veiculo);

    }

    public void aprovar(Long orcamentoId) {
        var orcamento = buscarEntidade(orcamentoId).orElseThrow(OrcamentoNaoEncontradaException::new);
        orcamentoDomainService.aprovar(orcamento);
        orcamentoRepository.save(orcamento);
    }

    public void recusar(Long orcamentoId) {
        var orcamento = buscarEntidade(orcamentoId).orElseThrow(OrcamentoNaoEncontradaException::new);
        orcamentoDomainService.recusar(orcamento);
        orcamentoRepository.save(orcamento);
    }

    public Optional<Orcamento> buscarEntidade(Long id) {
        return orcamentoRepository.findById(id);
    }

    public OrcamentoOutput buscarPorId(Long id) {

        var orcamento = orcamentoRepository.findById(id)
                .orElseThrow(OrcamentoNaoEncontradaException::new);

        var cliente = buscarCliente(orcamento.getClienteId());
        var veiculo = buscarVeiculo(cliente, orcamento.getVeiculoId());

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

    private Cliente buscarCliente(UUID clientId) {
        return clienteApplicationService.buscarEntidade(clientId)
                .orElseThrow(() ->
                        new OrcamentoException("Não foi encontrado o cliente vinculado ao orçamento informado"));
    }

    private Veiculo buscarVeiculo(Cliente cliente, UUID veiculoId) {
        return cliente.getVeiculoPorId(veiculoId)
                .orElseThrow(() -> new OrcamentoException("Não foi encontrado o veiculo vinculado ao orçamento informado"));
    }
}


