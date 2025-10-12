package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.OrdemServico;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.Status;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.exception.OrdemServicoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.out.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.usecase.impl.EntregarOrdemServicoUseCaseImpl;
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
class EntregarOrdemServicoUseCaseImplTest {

    @Mock
    private OrdemServicoRepository gateway;

    @InjectMocks
    private EntregarOrdemServicoUseCaseImpl entregarOrdemServicoUseCase;

    @Test
    void deveEntregarOrdemServicoComSucesso() {
        // Arrange
        var osId = UUID.randomUUID();
        var os = OrdemServico.from(osId, UUID.randomUUID(), UUID.randomUUID(), null, null, Status.FINALIZADA, LocalDateTime.now(), LocalDateTime.now(), null, LocalDateTime.now());
        var osSpy = spy(os);

        when(gateway.buscarPorId(osId)).thenReturn(Optional.of(osSpy));

        // Act
        entregarOrdemServicoUseCase.executar(osId);

        // Assert
        verify(osSpy).entregar();
        ArgumentCaptor<OrdemServico> osCaptor = ArgumentCaptor.forClass(OrdemServico.class);
        verify(gateway).salvar(osCaptor.capture());
        OrdemServico osSalva = osCaptor.getValue();

        assertEquals(Status.ENTREGUE, osSalva.getStatus());
    }

    @Test
    void deveLancarExcecaoQuandoOSNaoEncontradaParaEntregar() {
        // Arrange
        var osId = UUID.randomUUID();
        when(gateway.buscarPorId(osId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrdemServicoNaoEncontradaException.class, () -> {
            entregarOrdemServicoUseCase.executar(osId);
        });

        verify(gateway, never()).salvar(any(OrdemServico.class));
    }
}
