package br.com.alexsdm.postech.oficina.peca_insumo.core.usecase.impl;

import br.com.alexsdm.postech.oficina.peca_insumo.core.domain.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.peca_insumo.core.domain.exception.PecaInsumoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.peca_insumo.core.port.out.PecaInsumoRepository;
import br.com.alexsdm.postech.oficina.peca_insumo.core.usecase.input.AtualizarPecaInsumoInput;
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
class AtualizarPecaInsumoUseCaseImplTest {

    @Mock
    private PecaInsumoRepository pecaInsumoRepository;

    @InjectMocks
    private AtualizarPecaInsumoUseCaseImpl atualizarPecaInsumoUseCase;

    @Test
    void deveAtualizarPecaInsumoComSucesso() {
        // Arrange
        var pecaId = UUID.randomUUID();
        var input = new AtualizarPecaInsumoInput(
                pecaId,
                200,
                new BigDecimal("30.00"),
                new BigDecimal("55.00"),
                false
        );
        var pecaExistente = new PecaInsumo(pecaId, "Filtro", "Desc", "Cod", "Marca", 100, BigDecimal.ONE, BigDecimal.TEN, "Cat", true, LocalDateTime.now(), null);

        when(pecaInsumoRepository.buscarPorId(pecaId)).thenReturn(Optional.of(pecaExistente));

        // Act
        atualizarPecaInsumoUseCase.executar(input);

        // Assert
        ArgumentCaptor<PecaInsumo> pecaCaptor = ArgumentCaptor.forClass(PecaInsumo.class);
        verify(pecaInsumoRepository, times(1)).salvar(pecaCaptor.capture());
        PecaInsumo pecaSalva = pecaCaptor.getValue();

        assertEquals(200, pecaSalva.getQuantidadeEstoque());
        assertEquals(new BigDecimal("30.00"), pecaSalva.getPrecoCusto());
        assertEquals(new BigDecimal("55.00"), pecaSalva.getPrecoVenda());
        assertFalse(pecaSalva.getAtivo());
    }

    @Test
    void deveLancarExcecaoQuandoPecaInsumoNaoEncontradaParaAtualizar() {
        // Arrange
        var pecaId = UUID.randomUUID();
        var input = new AtualizarPecaInsumoInput(pecaId, 200, BigDecimal.ONE, BigDecimal.TEN, false);

        when(pecaInsumoRepository.buscarPorId(pecaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PecaInsumoNaoEncontradaException.class, () -> {
            atualizarPecaInsumoUseCase.executar(input);
        });

        verify(pecaInsumoRepository, never()).salvar(any(PecaInsumo.class));
    }
}
