package br.com.alexsdm.postech.oficina.ordemServico.service;

import br.com.alexsdm.postech.oficina.cliente.model.Cliente;
import br.com.alexsdm.postech.oficina.cliente.service.domain.ClienteDomainService;
import br.com.alexsdm.postech.oficina.orcamento.service.domain.OrcamentoDomainService;
import br.com.alexsdm.postech.oficina.orcamento.service.input.PecaOrcamentoInput;
import br.com.alexsdm.postech.oficina.ordemServico.controller.request.CriarOrdemDeServicoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.model.ItemPecaOrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.model.OrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.model.Status;
import br.com.alexsdm.postech.oficina.ordemServico.repository.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.ordemServico.service.input.OsPecaNecessariasInput;
import br.com.alexsdm.postech.oficina.ordemServico.service.output.*;
import br.com.alexsdm.postech.oficina.peca.service.PecaService;
import br.com.alexsdm.postech.oficina.servico.service.ServicoService;
import br.com.alexsdm.postech.oficina.veiculo.service.VeiculoModeloService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdemServicoService {

    private final VeiculoModeloService veiculoModeloService;
    private final PecaService pecaService;
    private final ServicoService servicoService;
    private final OrcamentoDomainService orcamentoService;
    private final OrdemServicoRepository ordemServicoRepository;
    private final ClienteDomainService clienteService;


    public Long criar(CriarOrdemDeServicoRequest criarOrdemDeServicoRequest) {
        var cliente = clienteService.buscarPorCpfCnpj(criarOrdemDeServicoRequest.cpfCnpj())
                .orElseThrow(RuntimeException::new);

        var veiculoId = criarOrdemDeServicoRequest.veiculoId();

        verificaSeVeiculoPertenceAoCliente(veiculoId, cliente);

        var orcamentoId = criarOrdemDeServicoRequest.orcamentoId();

        if (orcamentoId != null) {
            var orcamento = orcamentoService.buscarPorId(orcamentoId)
                    .orElseThrow(RuntimeException::new);
            if (!orcamento.isAceito()) {
                throw new RuntimeException("dsaodsaj");
            }


            var ordemServico = new OrdemServico(
                    cliente.getId(),
                    veiculoId,
                    Status.RECEBIDA
            );

            ordemServicoRepository.save(ordemServico);
            return ordemServico.getId();

        }
        var ordemServico = new OrdemServico(
                cliente.getId(),
                veiculoId,
                Status.RECEBIDA
        );
        ordemServicoRepository.save(ordemServico);
        return ordemServico.getId();
    }

    public VisualizarOrdemServiceOutput visualizarOrdemServico(Long ordemServicoId) {
        var ordemServico = buscarOrdemServico(ordemServicoId);
        var cliente = clienteService.buscar(ordemServico.getClienteId());

        var dadosCliente = new VisualizarOrdemServiceDadosClientOutput(cliente.getNome(), cliente.getCpfCnpj());
        var veiculoOs = cliente.getVeiculos()
                .stream()
                .filter(veiculo -> ordemServico.getVeiculoId().equals(veiculo.getId()))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        var dadosVeiculo = new VisualizarOrdemServiceDadosVeiculoOutput(veiculoOs.getPlaca(),
                veiculoOs.getVeiculoModelo().getMarca(),
                veiculoOs.getAno(),
                veiculoOs.getCor());

        var pecasInsumos = ordemServico.getItensPecaOrdemServico()
                .stream()
                .map(pecaInsumo ->
                        new VisualizarOrdemServicePecasInsumosOutput(pecaInsumo.getPeca().getNome(),
                                pecaInsumo.getPeca().getDescricao(),
                                pecaInsumo.getQuantidade()))
                .toList();

        var servicos = ordemServico.getServicos()
                .stream()
                .map(servico -> new VisualizarOrdemServicosOutput(servico.getNome(),
                        servico.getDescricao()))
                .toList();

        return new VisualizarOrdemServiceOutput(
                ordemServico.getId().toString(),
                ordemServico.getDataCriacao(),
                ordemServico.getDataInicioDaExecucao(),
                ordemServico.getDataFinalizacao(),
                ordemServico.getDataEntrega(),
                ordemServico.getStatus().toString(),
                dadosCliente,
                dadosVeiculo,
                pecasInsumos,
                servicos

        );
    }

    public void iniciarDiagnostico(Long idOrdemServico) {
        var ordemServico = buscarOrdemServico(idOrdemServico);
        ordemServico.diagnosticar();
        ordemServicoRepository.save(ordemServico);
    }

    public void finalizarDiagnostico(Long idOrdemServico,
                                     List<OsPecaNecessariasInput> osPecaNecessariasInputs,
                                     List<Long> idServicosNecessarios) {
        var ordemServico = buscarOrdemServico(idOrdemServico);
        var cliente = clienteService.buscar(ordemServico.getClienteId());
        ordemServico.finalizarDiagnostico();
        ordemServicoRepository.save(ordemServico);
        var orcamentoInput = osPecaNecessariasInputs.stream()
                .map(osPecaNecessariasInput ->
                        new PecaOrcamentoInput(osPecaNecessariasInput.pecaId(), osPecaNecessariasInput.qtd()))
                .toList();

        orcamentoService.criar(
                cliente.getCpfCnpj(),
                ordemServico.getVeiculoId(),
                orcamentoInput,
                idServicosNecessarios);
    }

    public void executar(Long idOrdemServico, Long orcamentoID) {
        var ordemServico = buscarOrdemServico(idOrdemServico);
        var orcamento = orcamentoService.buscarPorId(orcamentoID).orElseThrow(RuntimeException::new);
        //        if (!orcamento.isAceito()) {
        //            throw new RuntimeException("dsaodsaj");
        //        }

        orcamento
                .getItensPeca()
                .forEach(itemPecaOrcamento -> pecaService
                        .retirarItemEstoque(itemPecaOrcamento.getPeca(), itemPecaOrcamento.getQuantidade()));


        var itemPecaOS = orcamento
                .getItensPeca()
                .stream()
                .map(itemPecaOrcamento -> new ItemPecaOrdemServico(
                        itemPecaOrcamento.getPeca(),
                        itemPecaOrcamento.getPeca().getPrecoVenda(),
                        itemPecaOrcamento.getQuantidade(), ordemServico)).toList();

        ordemServico.executar(itemPecaOS, orcamento.getServicos());
        ordemServicoRepository.save(ordemServico);

    }

    public void finalizar(Long idOrdemServico) {
        var ordemServico = buscarOrdemServico(idOrdemServico);
        ordemServico.finalizar();
        ordemServicoRepository.save(ordemServico);
    }

    public void entregar(Long idOrdemServico) {
        var ordemServico = buscarOrdemServico(idOrdemServico);
        ordemServico.entregar();
        ordemServicoRepository.save(ordemServico);
    }

    private OrdemServico buscarOrdemServico(Long idOrdemServico) {
        return this.ordemServicoRepository
                .findById(idOrdemServico)
                .orElseThrow(RuntimeException::new);
    }

    private void verificaSeVeiculoPertenceAoCliente(UUID veiculoId, Cliente cliente) {
        var veiculoPertenceAoCliente = cliente.getVeiculos()
                .stream()
                .anyMatch(veiculo -> veiculoId.equals(veiculo.getId()));

        if (!veiculoPertenceAoCliente) {
            throw new RuntimeException("Veiculo informado invalido");
        }
    }

}
