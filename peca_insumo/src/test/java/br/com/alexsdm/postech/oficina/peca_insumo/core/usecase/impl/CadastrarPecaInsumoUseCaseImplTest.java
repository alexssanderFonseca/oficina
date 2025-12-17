package br.com.alexsdm.postech.oficina.peca_insumo.core.usecase.impl;

import br.com.alexsdm.postech.oficina.peca_insumo.core.domain.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.peca_insumo.core.port.out.PecaInsumoRepository;
import br.com.alexsdm.postech.oficina.peca_insumo.core.usecase.input.CadastrarPecaInsumoInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarPecaInsumoUseCaseImplTest {

    @Mock
    private PecaInsumoRepository pecaInsumoRepository;

    @InjectMocks
    private CadastrarPecaInsumoUseCaseImpl cadastrarPecaInsumoUseCase;

    @Test
    void deveCadastrarPecaInsumoComSucesso() {
        // Arrange
        var input = new CadastrarPecaInsumoInput(
                "Filtro de Óleo",
                "Filtro de óleo para motor 1.6",
                "FO-123",
                "MarcaX",
                100,
                new BigDecimal("25.00"),
                new BigDecimal("45.00"),
                "Filtros"
        );

        when(pecaInsumoRepository.salvar(any(PecaInsumo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        PecaInsumo pecaSalva = cadastrarPecaInsumoUseCase.executar(input);

        // Assert
        assertNotNull(pecaSalva);
        assertNotNull(pecaSalva.getId());
        assertTrue(pecaSalva.getAtivo());
        assertEquals("Filtro de Óleo", pecaSalva.getNome());
        assertEquals(new BigDecimal("45.00"), pecaSalva.getPrecoVenda());
    }
}
