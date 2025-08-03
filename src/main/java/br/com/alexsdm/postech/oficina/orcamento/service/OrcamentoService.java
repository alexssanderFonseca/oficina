package br.com.alexsdm.postech.oficina.orcamento.service;


import br.com.alexsdm.postech.oficina.cliente.service.ClienteService;
import br.com.alexsdm.postech.oficina.orcamento.model.ItemPecaOrcamento;
import br.com.alexsdm.postech.oficina.orcamento.model.Orcamento;
import br.com.alexsdm.postech.oficina.orcamento.model.OrcamentoStatus;
import br.com.alexsdm.postech.oficina.orcamento.repository.OrcamentoRepository;
import br.com.alexsdm.postech.oficina.orcamento.service.input.PecaOrcamentoInput;
import br.com.alexsdm.postech.oficina.peca.service.PecaService;
import br.com.alexsdm.postech.oficina.servico.service.ServicoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrcamentoService {

    private final PecaService pecaService;
    private final ServicoService servicoService;
    private final ClienteService clienteService;
    private final OrcamentoRepository orcamentoRepository;

    public OrcamentoService(PecaService pecaService,
                            ServicoService servicoService,
                            ClienteService clienteService, OrcamentoRepository orcamentoRepository) {
        this.pecaService = pecaService;
        this.servicoService = servicoService;
        this.clienteService = clienteService;
        this.orcamentoRepository = orcamentoRepository;
    }

    public Orcamento criar(String cpfCnpjCliente,
                           List<PecaOrcamentoInput> pecasOrcamentoInput,
                           List<Long> servicosId) {

        var cliente = clienteService.buscarPorCpfCnpj(cpfCnpjCliente)
                .orElseThrow(RuntimeException::new);

        var itens = pecasOrcamentoInput.stream()
                .map(input -> new ItemPecaOrcamento(
                        pecaService.buscarPorId(input.pecaId()),
                        input.qtd()))
                .toList();

        var servicos = servicosId.stream()
                .map(servicoService::buscar)
                .toList();

        var orcamento = new Orcamento(cliente, itens, servicos, OrcamentoStatus.CRIADO);

        return orcamentoRepository.save(orcamento);
    }

    //TODO gerar pdf
    public void enviar() {
    }

    public void aprovar(Long orcamentoId) {
        var orcamento = buscarPorId(orcamentoId).orElseThrow(RuntimeException::new);
        orcamento.aceitar();
        orcamentoRepository.save(orcamento);
    }

    public Optional<Orcamento> buscarPorId(Long id) {
        return orcamentoRepository.findById(id);
    }
}
