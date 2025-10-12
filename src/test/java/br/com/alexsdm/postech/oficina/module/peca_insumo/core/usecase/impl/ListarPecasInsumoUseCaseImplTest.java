package br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.peca_insumo.core.domain.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.domain.pagination.Page;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.port.out.PecaInsumoRepository;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.input.ListarPecasInsumoUseCaseInput;
import br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.output.ListarPecasInsumoUseCaseOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarPecasInsumoUseCaseImplTest {

    @Mock
    private PecaInsumoRepository pecaInsumoRepository;

    @InjectMocks
    private ListarPecasInsumoUseCaseImpl listarPecasInsumoUseCase;

    @Test
    void deveRetornarPaginaDePecasComSucesso() {
        // Arrange
        var input = new ListarPecasInsumoUseCaseInput(0L, 10L);
        var peca = new PecaInsumo(UUID.randomUUID(), "Filtro", "Desc", "Cod", "Marca", 10, BigDecimal.ONE, BigDecimal.TEN, "Cat", true, LocalDateTime.now(), null);
        var pecas = List.of(peca);
        var pageRequest = new Page<>(pecas, 1, 1, 0);

        when(pecaInsumoRepository.listarTodos(0L, 10L)).thenReturn(pageRequest);

        // Act
        Page<ListarPecasInsumoUseCaseOutput> resultPage = listarPecasInsumoUseCase.executar(input);

        // Assert
        assertNotNull(resultPage);
        assertEquals(1, resultPage.totalPaginas());
        assertEquals(0, resultPage.pagina());
        assertFalse(resultPage.conteudo().isEmpty());
        assertEquals(1, resultPage.conteudo().size());
        assertEquals("Filtro", resultPage.conteudo().get(0).nome());
    }

    @Test
    void deveRetornarPaginaVaziaQuandoNaoHouverPecas() {
        // Arrange
        var input = new ListarPecasInsumoUseCaseInput(0L, 10L);
        var pageRequest = new Page<PecaInsumo>(Collections.emptyList(), 0, 0, 0);

        when(pecaInsumoRepository.listarTodos(0L, 10L)).thenReturn(pageRequest);

        // Act
        Page<ListarPecasInsumoUseCaseOutput> resultPage = listarPecasInsumoUseCase.executar(input);

        // Assert
        assertNotNull(resultPage);
        assertEquals(0, resultPage.totalPaginas());
        assertEquals(0, resultPage.pagina());
        assertTrue(resultPage.conteudo().isEmpty());
    }
}
