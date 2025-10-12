package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.OrdemServico;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.Status;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.exception.OrdemServicoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.out.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.usecase.impl.FinalizarOrdemServicoUseCaseImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinalizarOrdemServicoUseCaseImplTest {

    @Mock
    private OrdemServicoRepository gateway;

    @InjectMocks
    private FinalizarOrdemServicoUseCaseImpl finalizarOrdemServicoUseCase;

    @Test
    void deveFinalizarOrdemServicoComSucesso() {
        // Arrange
        var osId = UUID.randomUUID();
        var os = OrdemServico.from(osId, UUID.randomUUID(), UUID.randomUUID(), null, null, Status.EM_EXECUCAO, LocalDateTime.now(), LocalDateTime.now(), null, null);
        var osSpy = spy(os);

        when(gateway.buscarPorId(osId)).thenReturn(Optional.of(osSpy));

        // Act
        finalizarOrdemServicoUseCase.executar(osId);

        // Assert
        verify(osSpy).finalizar();
        ArgumentCaptor<OrdemServico> osCaptor = ArgumentCaptor.forClass(OrdemServico.class);
        verify(gateway).salvar(osCaptor.capture());
        OrdemServico osSalva = osCaptor.getValue();

        assertEquals(Status.FINALIZADA, osSalva.getStatus());
    }

    @Test
    void deveLancarExcecaoQuandoOSNaoEncontradaParaFinalizar() {
        // Arrange
        var osId = UUID.randomUUID();
        when(gateway.buscarPorId(osId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrdemServicoNaoEncontradaException.class, () -> {
            finalizarOrdemServicoUseCase.executar(osId);
        });

        verify(gateway, never()).salvar(any(OrdemServico.class));
    }
}
