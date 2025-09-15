package br.com.alexsdm.postech.oficina.orcamento.application.usecase.impl;

import br.com.alexsdm.postech.oficina.orcamento.application.gateway.OrcamentoGateway;
import br.com.alexsdm.postech.oficina.orcamento.application.gateway.OrcamentoPecaInsumoGateway;
import br.com.alexsdm.postech.oficina.orcamento.application.gateway.OrcamentoServicoGateway;
import br.com.alexsdm.postech.oficina.orcamento.application.usecase.CriarOrcamentoUseCase;
import br.com.alexsdm.postech.oficina.orcamento.application.usecase.dto.CriarOrcamentoDTO;
import br.com.alexsdm.postech.oficina.orcamento.domain.entity.ItemPecaOrcamento;
import br.com.alexsdm.postech.oficina.orcamento.domain.entity.Orcamento;
import br.com.alexsdm.postech.oficina.orcamento.domain.entity.PecaOrcamento;
import br.com.alexsdm.postech.oficina.orcamento.domain.entity.ServicoOrcamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CriarOrcamentoUseCaseImpl implements CriarOrcamentoUseCase {

    private final OrcamentoPecaInsumoGateway pecaInsumoGateway;
    private final OrcamentoServicoGateway servicoGateway;
    private final OrcamentoGateway orcamentoGateway;

    @Override
    public Orcamento executar(CriarOrcamentoDTO dto) {
        List<ItemPecaOrcamento> itensPeca = new ArrayList<>();
        if (dto.pecas() != null) {
            itensPeca = dto.pecas().stream()
                    .map(this::criarItemPeca)
                    .collect(Collectors.toList());
        }

        List<ServicoOrcamento> servicos = new ArrayList<>();
        if (dto.servicosIds() != null) {
            servicos = dto.servicosIds().stream()
                    .map(this::criarServicoOrcamento)
                    .collect(Collectors.toList());
        }

        var orcamento = new Orcamento(dto.clienteId(), dto.veiculoId(), itensPeca, servicos);
        return orcamentoGateway.salvar(orcamento);
    }

    private ItemPecaOrcamento criarItemPeca(CriarOrcamentoDTO.ItemPecaDTO itemDTO) {
        var peca = pecaInsumoGateway.buscarPecaParaOrcamentoPorId(itemDTO.pecaId())
                .orElseThrow(() -> new RuntimeException("Peça não encontrada")); // TODO: Criar exceção específica
        var pecaOrcamento = new PecaOrcamento(peca.id(), peca.nome(), peca.precoVenda());
        return new ItemPecaOrcamento(pecaOrcamento, itemDTO.quantidade());
    }

    private ServicoOrcamento criarServicoOrcamento(Long servicoId) {
        var servico = servicoGateway.buscarServicoParaOrcamentoPorId(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado")); // TODO: Criar exceção específica
        return new ServicoOrcamento(servico.id(), servico.nome(), servico.preco());
    }
}
