package br.com.alexsdm.postech.oficina.ordemServico.service;

import br.com.alexsdm.postech.oficina.clientes.repository.ClienteRepository;
import br.com.alexsdm.postech.oficina.orcamento.repository.OrcamentoRepository;
import br.com.alexsdm.postech.oficina.ordemServico.controller.request.CriarOrdemDeServicoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.model.OrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.model.Status;
import br.com.alexsdm.postech.oficina.pecas.repository.PecaRepository;
import br.com.alexsdm.postech.oficina.servicos.repository.ServicoRepository;
import br.com.alexsdm.postech.oficina.veiculos.repository.VeiculoModeloRepository;

import java.util.Collections;

public class OrdemServicoService {

    private final ClienteRepository clienteRepository;
    private final VeiculoModeloRepository veiculoModeloRepository;
    private final OrcamentoRepository orcamentoRepository;
    private final PecaRepository pecasRepository;
    private final ServicoRepository servicoRepository;

    public OrdemServicoService(ClienteRepository clienteRepository,
                               VeiculoModeloRepository veiculoModeloRepository,
                               OrcamentoRepository orcamentoRepository,
                               PecaRepository pecasRepository,
                               ServicoRepository servicoRepository) {
        this.clienteRepository = clienteRepository;
        this.veiculoModeloRepository = veiculoModeloRepository;
        this.orcamentoRepository = orcamentoRepository;
        this.pecasRepository = pecasRepository;
        this.servicoRepository = servicoRepository;
    }

    public void criar(CriarOrdemDeServicoRequest criarOrdemDeServicoRequest) {
        var cliente = clienteRepository.findByCpfCnpj(criarOrdemDeServicoRequest.cpfCnpj())
                .orElseThrow(RuntimeException::new);

        var ordemService = new OrdemServico(
                cliente,
                Collections.emptyList(),
                Collections.emptyList(),
                Status.RECEBIDA
        );


    }
}
