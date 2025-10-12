package br.com.alexsdm.postech.oficina.module.orcamento.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.orcamento.core.entity.Orcamento;
import br.com.alexsdm.postech.oficina.module.orcamento.core.entity.OrcamentoStatus;
import br.com.alexsdm.postech.oficina.module.orcamento.core.exception.OrcamentoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.module.orcamento.core.port.out.OrcamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecusarOrcamentoUseCaseImplTest {

    @Mock
    private OrcamentoRepository orcamentoRepository;

    @InjectMocks
    private RecusarOrcamentoUseCaseImpl recusarOrcamentoUseCase;

    @Test
    void deveRecusarOrcamentoComSucesso() {
        // Arrange
        var orcamentoId = UUID.randomUUID();
        var orcamento = new Orcamento(orcamentoId, UUID.randomUUID(), UUID.randomUUID(), Collections.emptyList(), Collections.emptyList());
        assertEquals(OrcamentoStatus.CRIADO, orcamento.getStatus()); // Sanity check

        when(orcamentoRepository.buscarPorId(orcamentoId)).thenReturn(Optional.of(orcamento));

        // Act
        recusarOrcamentoUseCase.executar(orcamentoId);

        // Assert
        ArgumentCaptor<Orcamento> orcamentoCaptor = ArgumentCaptor.forClass(Orcamento.class);
        verify(orcamentoRepository, times(1)).salvar(orcamentoCaptor.capture());
        Orcamento orcamentoSalvo = orcamentoCaptor.getValue();

        assertEquals(OrcamentoStatus.RECUSADO, orcamentoSalvo.getStatus());
    }

    @Test
    void deveLancarExcecaoQuandoOrcamentoNaoEncontradoParaRecusar() {
        // Arrange
        var orcamentoId = UUID.randomUUID();
        when(orcamentoRepository.buscarPorId(orcamentoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrcamentoNaoEncontradaException.class, () -> {
            recusarOrcamentoUseCase.executar(orcamentoId);
        });

        verify(orcamentoRepository, never()).salvar(any(Orcamento.class));
    }
}
