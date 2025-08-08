package br.com.alexsdm.postech.oficina.ordemServico.service.application;

import br.com.alexsdm.postech.oficina.cliente.service.application.ClienteApplicationService;
import br.com.alexsdm.postech.oficina.orcamento.service.application.OrcamentoApplicationService;
import br.com.alexsdm.postech.oficina.orcamento.service.input.PecaOrcamentoInput;
import br.com.alexsdm.postech.oficina.ordemServico.controller.request.CriarOrdemDeServicoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.model.ItemPecaOrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.model.OrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.model.Status;
import br.com.alexsdm.postech.oficina.ordemServico.repository.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.ordemServico.service.application.input.OsPecaNecessariasInput;
import br.com.alexsdm.postech.oficina.ordemServico.service.application.output.*;
import br.com.alexsdm.postech.oficina.ordemServico.service.domain.OrdemServicoDomainService;
import br.com.alexsdm.postech.oficina.peca.service.application.PecaApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdemApplicationService {

    private final PecaApplicationService pecaApplicationService;
    private final OrcamentoApplicationService orcamentoApplicationService;
    private final OrdemServicoRepository ordemServicoRepository;
    private final OrdemServicoDomainService ordemServicoDomainService;
    private final ClienteApplicationService clienteApplicationService;


    public Long criar(CriarOrdemDeServicoRequest criarOrdemDeServicoRequest) {
        var cliente = clienteApplicationService.buscarPorCpfCnpj(criarOrdemDeServicoRequest.cpfCnpj())
                .orElseThrow(RuntimeException::new);

        var veiculoId = criarOrdemDeServicoRequest.veiculoId();

        cliente.getVeiculoPorId(veiculoId).orElseThrow(RuntimeException::new);

        var orcamentoId = criarOrdemDeServicoRequest.orcamentoId();

        if (orcamentoId != null) {
            var orcamento = orcamentoApplicationService.buscarEntidade(orcamentoId)
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

    public void iniciarDiagnostico(Long idOrdemServico) {
        var ordemServico = buscarOrdemServico(idOrdemServico);
        ordemServicoDomainService.iniciarDiagnostico(ordemServico);
        ordemServicoRepository.save(ordemServico);
    }

    public void finalizarDiagnostico(Long idOrdemServico,
                                     List<OsPecaNecessariasInput> osPecaNecessariasInputs,
                                     List<Long> idServicosNecessarios) {
        var ordemServico = buscarOrdemServico(idOrdemServico);

        var cliente = clienteApplicationService.buscarEntidade(ordemServico.getClienteId()).get();

        ordemServicoDomainService.finalizarDiagnostico(ordemServico);
        ordemServicoRepository.save(ordemServico);

        var orcamentoInput = osPecaNecessariasInputs.stream()
                .map(osPecaNecessariasInput ->
                        new PecaOrcamentoInput(osPecaNecessariasInput.pecaId(), osPecaNecessariasInput.qtd()))
                .toList();

        orcamentoApplicationService.criar(
                cliente.getCpfCnpj(),
                ordemServico.getVeiculoId(),
                orcamentoInput,
                idServicosNecessarios);
    }

    public void executar(Long idOrdemServico, Long orcamentoID) {
        var ordemServico = buscarOrdemServico(idOrdemServico);
        var orcamento = orcamentoApplicationService.buscarEntidade(orcamentoID)
                .orElseThrow(RuntimeException::new);
        if (!orcamento.isAceito()) {
            throw new RuntimeException("dsaodsaj");
        }

        orcamento
                .getItensPeca()
                .forEach(itemPecaOrcamento -> pecaApplicationService
                        .retirarItemEstoque(itemPecaOrcamento.getPeca().getId(),
                                itemPecaOrcamento.getQuantidade()));


        var itemPecaOS = orcamento
                .getItensPeca()
                .stream()
                .map(itemPecaOrcamento -> new ItemPecaOrdemServico(
                        itemPecaOrcamento.getPeca(),
                        itemPecaOrcamento.getPeca().getPrecoVenda(),
                        itemPecaOrcamento.getQuantidade(), ordemServico)).toList();

        ordemServicoDomainService.executar(ordemServico, itemPecaOS, orcamento.getServicos());
        ordemServicoRepository.save(ordemServico);

    }

    public void finalizar(Long idOrdemServico) {
        var ordemServico = buscarOrdemServico(idOrdemServico);
        ordemServicoDomainService.finalizar(ordemServico);
        ordemServicoRepository.save(ordemServico);
    }

    public void entregar(Long idOrdemServico) {
        var ordemServico = buscarOrdemServico(idOrdemServico);
        ordemServicoDomainService.entregar(ordemServico);
        ordemServicoRepository.save(ordemServico);
    }

    private OrdemServico buscarOrdemServico(Long idOrdemServico) {
        return this.ordemServicoRepository
                .findById(idOrdemServico)
                .orElseThrow(RuntimeException::new);
    }

    public VisualizarOrdemServiceOutput visualizarOrdemServico(Long ordemServicoId) {
        var ordemServico = buscarOrdemServico(ordemServicoId);
        var cliente = clienteApplicationService.buscarEntidade(ordemServico.getClienteId()).get();

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


}
