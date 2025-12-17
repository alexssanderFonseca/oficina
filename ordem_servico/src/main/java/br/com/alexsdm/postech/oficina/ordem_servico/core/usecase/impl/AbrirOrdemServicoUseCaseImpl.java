package br.com.alexsdm.postech.oficina.ordem_servico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.ordem_servico.core.domain.entity.*;
import br.com.alexsdm.postech.oficina.ordem_servico.core.domain.exception.OrdemServicoItemNaoEncontradoException;
import br.com.alexsdm.postech.oficina.ordem_servico.core.domain.exception.OrdemServicoServicoNaoEncontradoException;
import br.com.alexsdm.postech.oficina.ordem_servico.core.port.in.AbrirOrdemServicoUseCase;
import br.com.alexsdm.postech.oficina.ordem_servico.core.port.out.OrdemServicoPecaInsumoPort;
import br.com.alexsdm.postech.oficina.ordem_servico.core.port.out.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.ordem_servico.core.port.out.OrdemServicoServicoPort;
import br.com.alexsdm.postech.oficina.ordem_servico.core.usecase.input.CriarOrdemServicoInput;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Named
@RequiredArgsConstructor
public class AbrirOrdemServicoUseCaseImpl implements AbrirOrdemServicoUseCase {

    private final OrdemServicoRepository ordemServicoRepository;
    private final OrdemServicoPecaInsumoPort ordemServicoPecaPort;
    private final OrdemServicoServicoPort ordemServicoServicoPort;

    @Override
    public UUID executar(CriarOrdemServicoInput input) {
        var ordemServico = abrirOrdemServico(input);
        ordemServicoRepository.salvar(ordemServico);
        return ordemServico.getId();
    }

    private OrdemServico abrirOrdemServico(CriarOrdemServicoInput input) {
        if (possuitensEServicos(input)) {
            return abrirOrdemServicoParaExecucao(input);
        }

        return abrirOrdemServicoParaDiagnostico(input.clienteId(), input.veiculoId());
    }

    private boolean possuitensEServicos(CriarOrdemServicoInput input) {
        var possuiServicos = Optional.ofNullable(input.servicos())
                .filter(servicos -> !servicos.isEmpty())
                .isPresent();

        var possuiItens = Optional.ofNullable(input.pecasInsumos())
                .filter(servicos -> !servicos.isEmpty())
                .isPresent();

        return possuiServicos && possuiItens;

    }

    private OrdemServico abrirOrdemServicoParaDiagnostico(UUID clienteId,
                                                          UUID veiculoId) {
        return OrdemServico.criarOrdemServicoParaDiagnostico(clienteId, veiculoId);
    }

    private OrdemServico abrirOrdemServicoParaExecucao(CriarOrdemServicoInput input) {
        return OrdemServico.criarOrdemServicoParaExecucao(
                input.clienteId(),
                input.veiculoId(),
                montarItensPecaInsumoNecessariosOs(input.pecasInsumos()),
                montarItensServicosNecessariosOs(input.servicos())
        );
    }

    private List<ItemPecaOrdemServico> montarItensPecaInsumoNecessariosOs(List<CriarOrdemServicoInput.CriarOrdemServicoItemInsumoInput> pecasInsumos) {
        return pecasInsumos
                .stream()
                .map(pecaInsumo -> {
                    var pecaInsumoCompleto = buscarPeca(pecaInsumo.idPecaInsumo());
                    return new ItemPecaOrdemServico(
                            UUID.randomUUID(),
                            pecaInsumoCompleto.id(),
                            pecaInsumoCompleto.nome(),
                            pecaInsumoCompleto.precoVenda(),
                            pecaInsumoCompleto.descricao(),
                            pecaInsumo.qtd());
                }).toList();

    }

    private List<ItemServicoOrdemServico> montarItensServicosNecessariosOs(List<UUID> servicosIds) {
        return servicosIds
                .stream()
                .map(idServico -> {
                    var servico = buscarServico(idServico);
                    return new ItemServicoOrdemServico(UUID.randomUUID(),
                            servico.id(),
                            servico.nome(),
                            servico.descricao(),
                            servico.preco());
                }).toList();

    }

    private PecaInsumo buscarPeca(UUID pecaId) {
        return ordemServicoPecaPort.buscarPecaInsumo(pecaId)
                .orElseThrow(() -> new OrdemServicoItemNaoEncontradoException(pecaId));
    }

    private Servico buscarServico(UUID servicoId) {
        return ordemServicoServicoPort.buscarServicoPorId(servicoId)
                .orElseThrow(() -> new OrdemServicoServicoNaoEncontradoException(servicoId));
    }


}
