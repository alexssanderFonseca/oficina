package br.com.alexsdm.postech.oficina.orcamento.core.usecase.impl;

import br.com.alexsdm.postech.oficina.orcamento.core.port.out.ClienteOrcamentoPort;
import br.com.alexsdm.postech.oficina.orcamento.core.port.out.OrcamentoRepository;
import br.com.alexsdm.postech.oficina.orcamento.core.port.out.OrcamentoPecaInsumoPort;
import br.com.alexsdm.postech.oficina.orcamento.core.port.out.OrcamentoServicoPort;
import br.com.alexsdm.postech.oficina.orcamento.core.port.in.CriarOrcamentoUseCase;
import br.com.alexsdm.postech.oficina.orcamento.core.usecase.input.CriarOrcamentoInput;
import br.com.alexsdm.postech.oficina.orcamento.core.entity.Cliente;
import br.com.alexsdm.postech.oficina.orcamento.core.entity.ItemPecaOrcamento;
import br.com.alexsdm.postech.oficina.orcamento.core.entity.ItemServicoOrcamento;
import br.com.alexsdm.postech.oficina.orcamento.core.entity.Orcamento;
import br.com.alexsdm.postech.oficina.orcamento.core.exception.OrcamentoException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Named
@RequiredArgsConstructor
public class CriarOrcamentoUseCaseImpl implements CriarOrcamentoUseCase {

    private final OrcamentoPecaInsumoPort pecaInsumoGateway;
    private final OrcamentoServicoPort servicoGateway;
    private final OrcamentoRepository orcamentoRepository;
    private final ClienteOrcamentoPort clienteOrcamentoPort;

    @Override
    public UUID executar(CriarOrcamentoInput input) {

        var cliente = buscarCliente(input)
                .orElseThrow(() -> new OrcamentoException("Cliente não encontrado para gerar o orcamento"));

        var orcamento = new Orcamento(
                UUID.randomUUID(),
                cliente.getId(),
                input.veiculoId(),
                obterItensOrcamento(input),
                obterServicosOrcamento(input));

        return orcamentoRepository.salvar(orcamento).getId();
    }

    private Optional<Cliente> buscarCliente(CriarOrcamentoInput input) {
        if (input.cpfCnpjCliente() == null && input.idCliente() == null) {
            throw new IllegalArgumentException("Identificador do cliente é obrigatorio");
        }
        if (input.cpfCnpjCliente() != null) {
            return clienteOrcamentoPort.buscarClienteComVeiculo(input.cpfCnpjCliente(),
                    input.veiculoId());
        }
        return clienteOrcamentoPort.buscarClienteComVeiculo(UUID.fromString(input.idCliente()),
                input.veiculoId());
    }

    private List<ItemPecaOrcamento> obterItensOrcamento(CriarOrcamentoInput input) {
        return input.pecas().stream()
                .map(this::criarItemPeca)
                .toList();
    }

    private List<ItemServicoOrcamento> obterServicosOrcamento(CriarOrcamentoInput input) {
        return input.servicosIds().stream()
                .map(this::criarServicoOrcamento)
                .toList();
    }


    private ItemPecaOrcamento criarItemPeca(CriarOrcamentoInput.CriarOrcamentoItemPecaInput itemDTO) {
        var peca = pecaInsumoGateway.buscarPecaParaOrcamentoPorId(itemDTO.pecaId())
                .orElseThrow(() -> new OrcamentoException("Peça não encontrada"));
        return new ItemPecaOrcamento(UUID.randomUUID(),
                peca.id(),
                peca.nome(),
                itemDTO.quantidade(),
                peca.precoVenda(),
                peca.descricao());
    }

    private ItemServicoOrcamento criarServicoOrcamento(UUID servicoId) {
        var servico = servicoGateway.buscarServicoParaOrcamentoPorId(servicoId)
                .orElseThrow(() -> new OrcamentoException("Serviço não encontrado"));
        return new ItemServicoOrcamento(UUID.randomUUID(),
                servico.id(),
                servico.nome(),
                servico.descricao(),
                servico.preco());
    }
}
