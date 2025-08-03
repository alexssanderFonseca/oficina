package br.com.alexsdm.postech.oficina.ordemServico.service;

import br.com.alexsdm.postech.oficina.cliente.repository.ClienteRepository;
import br.com.alexsdm.postech.oficina.orcamento.service.OrcamentoService;
import br.com.alexsdm.postech.oficina.orcamento.service.input.PecaOrcamentoInput;
import br.com.alexsdm.postech.oficina.ordemServico.controller.request.CriarOrdemDeServicoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.model.ItemPecaOrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.model.OrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.model.Status;
import br.com.alexsdm.postech.oficina.ordemServico.repository.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.ordemServico.service.input.OsPecaNecessariasInput;
import br.com.alexsdm.postech.oficina.peca.repository.PecaRepository;
import br.com.alexsdm.postech.oficina.servico.repository.ServicoRepository;
import br.com.alexsdm.postech.oficina.veiculo.repository.VeiculoModeloRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdemServicoService {

    private final ClienteRepository clienteRepository;
    private final VeiculoModeloRepository veiculoModeloRepository;
    private final PecaRepository pecasRepository;
    private final ServicoRepository servicoRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    private final OrcamentoService orcamentoService;

    public OrdemServicoService(ClienteRepository clienteRepository,
                               VeiculoModeloRepository veiculoModeloRepository,
                               PecaRepository pecasRepository,
                               ServicoRepository servicoRepository, OrdemServicoRepository ordemServicoRepository, OrcamentoService orcamentoService) {
        this.clienteRepository = clienteRepository;
        this.veiculoModeloRepository = veiculoModeloRepository;
        this.pecasRepository = pecasRepository;
        this.servicoRepository = servicoRepository;
        this.ordemServicoRepository = ordemServicoRepository;
        this.orcamentoService = orcamentoService;
    }

    public OrdemServico criar(CriarOrdemDeServicoRequest criarOrdemDeServicoRequest) {
        var cliente = clienteRepository.findByCpfCnpj(criarOrdemDeServicoRequest.cpfCnpj())
                .orElseThrow(RuntimeException::new);

        var orcamentoId = criarOrdemDeServicoRequest.orcamentoId();

        if (orcamentoId != null) {
            var orcamento = orcamentoService.buscarPorId(orcamentoId)
                    .orElseThrow(RuntimeException::new);
            if (!orcamento.isAceito()) {
                throw new RuntimeException("dsaodsaj");
            }

            var ordemServico = new OrdemServico(
                    cliente,
                    Status.RECEBIDA
            );

            ordemServicoRepository.save(ordemServico);
            return ordemServico;

        }
        var ordemServico = new OrdemServico(
                cliente,
                Status.RECEBIDA
        );
        ordemServicoRepository.save(ordemServico);
        return ordemServico;
    }

    public OrdemServico iniciarDiagnostico(Long idOrdemServico) {
        var ordemServico = buscarOrdemServico(idOrdemServico);
        ordemServico.diagnosticar();
        ordemServicoRepository.save(ordemServico);
        return ordemServico;
    }

    public OrdemServico finalizarDiagnostico(Long idOrdemServico,
                                             List<OsPecaNecessariasInput> osPecaNecessariasInputs,
                                             List<Long> idServicosNecessarios) {
        var ordemServico = buscarOrdemServico(idOrdemServico);
        ordemServico.finalizarDiagnostico();
        ordemServicoRepository.save(ordemServico);
        var orcamentoInput = osPecaNecessariasInputs.stream()
                .map(osPecaNecessariasInput ->
                        new PecaOrcamentoInput(osPecaNecessariasInput.pecaId(), osPecaNecessariasInput.qtd()))
                .toList();

        orcamentoService.criar(
                ordemServico.getCliente().getCpfCnpj(),
                orcamentoInput,
                idServicosNecessarios);

        return ordemServico;

    }

    public OrdemServico executar(Long idOrdemServico, Long orcamentoID) {
        var ordemServico = buscarOrdemServico(idOrdemServico);
        var orcamento = orcamentoService.buscarPorId(orcamentoID).orElseThrow(RuntimeException::new);
        //        if (!orcamento.isAceito()) {
        //            throw new RuntimeException("dsaodsaj");
        //        }
        var itemPecaOS = orcamento.getItensPeca().stream()
                .map(itemPecaOrcamento -> {
                    itemPecaOrcamento.getPeca().retirar(itemPecaOrcamento.getQuantidade());
                    return new ItemPecaOrdemServico(itemPecaOrcamento.getPeca(), itemPecaOrcamento.getQuantidade(), ordemServico);
                }).toList();

        ordemServico.executar(itemPecaOS, orcamento.getServicos());
        ordemServicoRepository.save(ordemServico);
        return ordemServico;

    }

    public void finalizar(Long idOrdemServico) {
        var ordemServico = buscarOrdemServico(idOrdemServico);
        ordemServico.finalizar();
        ordemServicoRepository.save(ordemServico);
    }

    public OrdemServico entregar(Long idOrdemServico) {
        var ordemServico = buscarOrdemServico(idOrdemServico);
        ordemServico.entregar();
        ordemServicoRepository.save(ordemServico);
        return ordemServico;
    }

    private OrdemServico buscarOrdemServico(Long idOrdemServico) {
        return this.ordemServicoRepository
                .findById(idOrdemServico)
                .orElseThrow(RuntimeException::new);
    }
}
