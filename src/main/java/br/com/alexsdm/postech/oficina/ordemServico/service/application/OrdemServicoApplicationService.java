package br.com.alexsdm.postech.oficina.ordemServico.service.application;

import br.com.alexsdm.postech.oficina.cliente.service.application.ClienteApplicationService;
import br.com.alexsdm.postech.oficina.orcamento.service.application.OrcamentoApplicationService;
import br.com.alexsdm.postech.oficina.orcamento.service.input.PecaOrcamentoInput;
import br.com.alexsdm.postech.oficina.ordemServico.controller.request.CriarOrdemDeServicoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.entity.ItemPecaOrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.entity.OrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.entity.Status;
import br.com.alexsdm.postech.oficina.ordemServico.exception.*;
import br.com.alexsdm.postech.oficina.ordemServico.repository.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.ordemServico.service.application.input.OsPecaNecessariasInput;
import br.com.alexsdm.postech.oficina.ordemServico.service.application.output.*;
import br.com.alexsdm.postech.oficina.ordemServico.service.domain.OrdemServicoDomainService;
import br.com.alexsdm.postech.oficina.pecaInsumo.service.application.PecaInsumoApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdemServicoApplicationService {

    private final PecaInsumoApplicationService pecaApplicationService;
    private final OrcamentoApplicationService orcamentoApplicationService;
    private final OrdemServicoRepository ordemServicoRepository;
    private final OrdemServicoDomainService ordemServicoDomainService;
    private final ClienteApplicationService clienteApplicationService;


    public Long criar(CriarOrdemDeServicoRequest criarOrdemDeServicoRequest) {
        var cliente = clienteApplicationService.buscarPorCpfCnpj(criarOrdemDeServicoRequest.cpfCnpj())
                .orElseThrow(OrdemServicoClienteNaoEncontradoException::new);

        var veiculoId = criarOrdemDeServicoRequest.veiculoId();

        cliente.getVeiculoPorId(veiculoId).orElseThrow(OrdemServicoVeiculoNaoEncontradoException::new);

        var orcamentoId = criarOrdemDeServicoRequest.orcamentoId();

        if (orcamentoId != null) {
            var orcamento = orcamentoApplicationService.buscarEntidade(orcamentoId)
                    .orElseThrow(OrdemServicoOrcamentoNaoEncontradoException::new);
            if (!orcamento.isAceito()) {
                throw new OrdemServicoException("Ordem de serviço não pode ser executada, sem o aceite do orçamento");
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

        var cliente = clienteApplicationService.buscarEntidade(ordemServico.getClienteId()).
                orElseThrow(OrdemServicoClienteNaoEncontradoException::new);


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
                .orElseThrow(() ->
                        new OrdemServicoException("Orcamento relacionado a ordem de servico não encontrada"));

        if (!orcamento.isAceito()) {
            throw new OrdemServicoException("A ordem de servico não pode ser executada, sem aceite do orcamento");
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
                .orElseThrow(OrdemServicoNaoEncontradaException::new);
    }

    public VisualizarOrdemServiceOutput visualizarOrdemServico(Long ordemServicoId) {
        var ordemServico = buscarOrdemServico(ordemServicoId);
        var cliente = clienteApplicationService.buscarEntidade(ordemServico.getClienteId()).
                orElseThrow(OrdemServicoClienteNaoEncontradoException::new);

        var dadosCliente = new VisualizarOrdemServiceDadosClientOutput(cliente.getNome(), cliente.getCpfCnpj());

        var veiculoOs = cliente.getVeiculos()
                .stream()
                .filter(veiculo -> ordemServico.getVeiculoId().equals(veiculo.getId()))
                .findFirst()
                .orElseThrow(OrdemServicoVeiculoNaoEncontradoException::new);

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
                ordemServico.getId(),
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

    public Double getTempoMedioExecucaoServicosEmSegundos() {
        return this.ordemServicoRepository.calcularTempoMedioExecucaoSegundos();
    }

    public PaginaResumida<OrdemServicoAcompanhamentoOutput> listarOrdensServico(Pageable pageable) {
        var page = ordemServicoRepository.findAll(pageable);

        var content = page.getContent().stream()
                .map(os -> new OrdemServicoAcompanhamentoOutput(
                        os.getId(),
                        os.getDataCriacao(),
                        os.getDataInicioDaExecucao(),
                        os.getDataFinalizacao(),
                        os.getDataEntrega(),
                        os.getStatus().name()
                ))
                .toList();

        return new PaginaResumida<>(content, page.getTotalElements(), page.getNumber());
    }


    public List<OrdemServico> statusByCliente(UUID clientId) {
        return this.ordemServicoRepository.findAllByClienteId(clientId);
    }


}
