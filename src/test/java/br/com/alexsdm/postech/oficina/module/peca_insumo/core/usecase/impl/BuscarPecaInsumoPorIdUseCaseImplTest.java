package br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.peca_insumo.core.domain.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.domain.exception.PecaInsumoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.port.out.PecaInsumoRepository;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.output.BuscarPecaInsumoPorIdUseCaseOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarPecaInsumoPorIdUseCaseImplTest {

    @Mock
    private PecaInsumoRepository pecaInsumoRepository;

    @InjectMocks
    private BuscarPecaInsumoPorIdUseCaseImpl buscarPecaInsumoPorIdUseCase;

    @Test
    void deveBuscarPecaInsumoPorIdComSucesso() {
        // Arrange
        var pecaId = UUID.randomUUID();
        var pecaEsperada = new PecaInsumo(pecaId, "Filtro", "Desc", "Cod", "Marca", 10, BigDecimal.ONE, BigDecimal.TEN, "Cat", true, LocalDateTime.now(), null);

        when(pecaInsumoRepository.buscarPorId(pecaId)).thenReturn(Optional.of(pecaEsperada));

        // Act
        BuscarPecaInsumoPorIdUseCaseOutput pecaAtual = buscarPecaInsumoPorIdUseCase.executar(pecaId);

        // Assert
        assertNotNull(pecaAtual);
        assertEquals(pecaEsperada.getId(), pecaAtual.id());
        assertEquals("Filtro", pecaAtual.nome());
    }

    @Test
    void deveLancarExcecaoQuandoPecaInsumoNaoEncontrada() {
        // Arrange
        var pecaId = UUID.randomUUID();
        when(pecaInsumoRepository.buscarPorId(pecaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PecaInsumoNaoEncontradaException.class, () -> {
            buscarPecaInsumoPorIdUseCase.executar(pecaId);
        });
    }
}
