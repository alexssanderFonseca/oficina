package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.*;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.exception.OrdemServicoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.out.OrdemServicoOrcamentoPort;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.out.OrdemServicoPecaInsumoPort;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.out.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.out.OrdemServicoServicoPort;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.usecase.impl.FinalizarDiagnosticoUseCaseImpl;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.usecase.input.FinalizarDiagnosticoInput;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.usecase.output.FinalizarDiagnosticoOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinalizarDiagnosticoUseCaseImplTest {

    @Mock
    private OrdemServicoRepository ordemServicoRepository;
    @Mock
    private OrdemServicoOrcamentoPort ordemServicoOrcamentoPort;
    @Mock
    private OrdemServicoPecaInsumoPort ordemServicoPecaPort;
    @Mock
    private OrdemServicoServicoPort ordemServicoServicoPort;

    @InjectMocks
    private FinalizarDiagnosticoUseCaseImpl finalizarDiagnosticoUseCase;

    @Test
    void deveFinalizarDiagnosticoComSucesso() {
        // Arrange
        var osId = UUID.randomUUID();
        var pecaId = UUID.randomUUID();
        var servicoId = UUID.randomUUID();
        var itemPecaInput = new FinalizarDiagnosticoInput.FinalizarDiagnosticoItemPecaInput(pecaId, 1);
        var input = new FinalizarDiagnosticoInput(osId, List.of(itemPecaInput), List.of(servicoId));

        var os = OrdemServico.from(osId, UUID.randomUUID(), UUID.randomUUID(), null, null, Status.EM_DIAGNOSTICO, LocalDateTime.now(), null, null, null);
        var osSpy = spy(os); // Usar spy para verificar a chamada do método

        var peca = new PecaInsumo(pecaId, "Peca", "Desc", 1, BigDecimal.TEN);
        var servico = new Servico(servicoId, "Servico", "Desc", BigDecimal.ONE);
        var orcamentoId = UUID.randomUUID();

        when(ordemServicoRepository.buscarPorId(osId)).thenReturn(Optional.of(osSpy));
        when(ordemServicoPecaPort.buscarPecaInsumo(pecaId)).thenReturn(Optional.of(peca));
        when(ordemServicoServicoPort.buscarServicoPorId(servicoId)).thenReturn(Optional.of(servico));
        when(ordemServicoOrcamentoPort.criar(any(Orcamento.class))).thenReturn(orcamentoId);

        // Act
        FinalizarDiagnosticoOutput output = finalizarDiagnosticoUseCase.executar(input);

        // Assert
        assertNotNull(output);
        assertEquals(orcamentoId, output.orcamentoId());
        verify(osSpy).finalizarDiagnostico();
    }

    @Test
    void deveLancarExcecaoQuandoOSNaoEncontradaParaFinalizarDiagnostico() {
        var input = new FinalizarDiagnosticoInput(UUID.randomUUID(), List.of(), List.of());
        when(ordemServicoRepository.buscarPorId(input.osId())).thenReturn(Optional.empty());
        assertThrows(OrdemServicoNaoEncontradaException.class, () -> finalizarDiagnosticoUseCase.executar(input));
    }
}
