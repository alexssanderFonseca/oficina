package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.*;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.exception.OrdemServicoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.exception.OrdemServicoOrcamentoNaoEncontradoException;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.out.OrdemServicoOrcamentoPort;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.out.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.usecase.impl.ExecutarOrdemServicoUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExecutarOrdemServicoUseCaseImplTest {

    @Mock
    private OrdemServicoRepository ordemServicoRepository;
    @Mock
    private OrdemServicoOrcamentoPort orcamentoGateway;

    @InjectMocks
    private ExecutarOrdemServicoUseCaseImpl executarOrdemServicoUseCase;

    @Test
    void deveExecutarOrdemServicoComSucesso() {
        // Arrange
        var osId = UUID.randomUUID();
        var orcamentoId = UUID.randomUUID();
        var os = OrdemServico.from(osId, UUID.randomUUID(), UUID.randomUUID(), null, null, Status.AGUARDANDO_APROVACAO, LocalDateTime.now(), null, null, null);
        var osSpy = spy(os);

        var itemPeca = new ItemPecaOrcamento(UUID.randomUUID(), 1, "Peca", "Desc", BigDecimal.TEN);
        var itemServico = new ItemServicoOrcamento(UUID.randomUUID(), UUID.randomUUID(), "Servico", "Desc", BigDecimal.ONE);
        var orcamento = new Orcamento(orcamentoId, null, null, List.of(itemServico), List.of(itemPeca));

        when(ordemServicoRepository.buscarPorId(osId)).thenReturn(Optional.of(osSpy));
        when(orcamentoGateway.buscarPorId(orcamentoId)).thenReturn(Optional.of(orcamento));

        // Act
        executarOrdemServicoUseCase.executar(osId, orcamentoId);

        // Assert
        verify(osSpy).executar(anyList(), anyList());
        ArgumentCaptor<OrdemServico> osCaptor = ArgumentCaptor.forClass(OrdemServico.class);
        verify(ordemServicoRepository).salvar(osCaptor.capture());
        OrdemServico osSalva = osCaptor.getValue();

        assertEquals(Status.EM_EXECUCAO, osSalva.getStatus());
        assertFalse(osSalva.getItensPecaOrdemServico().isEmpty());
        assertFalse(osSalva.getServicos().isEmpty());
    }

    @Test
    void deveLancarExcecaoQuandoOSNaoEncontradaParaExecutar() {
        var osId = UUID.randomUUID();
        var orcamentoId = UUID.randomUUID();
        when(ordemServicoRepository.buscarPorId(osId)).thenReturn(Optional.empty());
        assertThrows(OrdemServicoNaoEncontradaException.class, () -> executarOrdemServicoUseCase.executar(osId, orcamentoId));
    }

    @Test
    void deveLancarExcecaoQuandoOrcamentoNaoEncontradoParaExecutar() {
        var osId = UUID.randomUUID();
        var orcamentoId = UUID.randomUUID();
        var os = OrdemServico.from(osId, UUID.randomUUID(), UUID.randomUUID(), null, null, Status.AGUARDANDO_APROVACAO, null, null, null, null);

        when(ordemServicoRepository.buscarPorId(osId)).thenReturn(Optional.of(os));
        when(orcamentoGateway.buscarPorId(orcamentoId)).thenReturn(Optional.empty());

        assertThrows(OrdemServicoOrcamentoNaoEncontradoException.class, () -> executarOrdemServicoUseCase.executar(osId, orcamentoId));
    }
}
