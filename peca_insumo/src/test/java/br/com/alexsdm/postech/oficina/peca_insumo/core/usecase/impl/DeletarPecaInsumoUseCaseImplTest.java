package br.com.alexsdm.postech.oficina.peca_insumo.core.usecase.impl;

import br.com.alexsdm.postech.oficina.peca_insumo.core.domain.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.peca_insumo.core.domain.exception.PecaInsumoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.peca_insumo.core.port.out.PecaInsumoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarPecaInsumoUseCaseImplTest {

    @Mock
    private PecaInsumoRepository pecaInsumoRepository;

    @InjectMocks
    private DeletarPecaInsumoUseCaseImpl deletarPecaInsumoUseCase;

    @Test
    void deveInativarPecaInsumoComSucesso() {
        // Arrange
        var pecaId = UUID.randomUUID();
        var pecaExistente = new PecaInsumo(pecaId, "Filtro", "Desc", "Cod", "Marca", 100, BigDecimal.ONE, BigDecimal.TEN, "Cat", true, LocalDateTime.now(), null);
        assertTrue(pecaExistente.getAtivo()); // Sanity check

        when(pecaInsumoRepository.buscarPorId(pecaId)).thenReturn(Optional.of(pecaExistente));

        // Act
        deletarPecaInsumoUseCase.executar(pecaId);

        // Assert
        ArgumentCaptor<PecaInsumo> pecaCaptor = ArgumentCaptor.forClass(PecaInsumo.class);
        verify(pecaInsumoRepository, times(1)).salvar(pecaCaptor.capture());
        PecaInsumo pecaSalva = pecaCaptor.getValue();

        assertFalse(pecaSalva.getAtivo());
    }

    @Test
    void deveLancarExcecaoQuandoPecaInsumoNaoEncontradaParaDeletar() {
        // Arrange
        var pecaId = UUID.randomUUID();
        when(pecaInsumoRepository.buscarPorId(pecaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PecaInsumoNaoEncontradaException.class, () -> {
            deletarPecaInsumoUseCase.executar(pecaId);
        });

        verify(pecaInsumoRepository, never()).salvar(any(PecaInsumo.class));
    }
}
