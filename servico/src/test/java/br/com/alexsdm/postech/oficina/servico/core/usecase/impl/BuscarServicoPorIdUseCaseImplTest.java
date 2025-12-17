package br.com.alexsdm.postech.oficina.servico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.servico.core.domain.entity.Servico;
import br.com.alexsdm.postech.oficina.servico.core.domain.exception.ServicoNaoEncontradoException;
import br.com.alexsdm.postech.oficina.servico.core.port.out.ServicoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarServicoPorIdUseCaseImplTest {

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private BuscarServicoPorIdUseCaseImpl buscarServicoPorIdUseCase;

    @Test
    void deveBuscarServicoPorIdComSucesso() {
        // Arrange
        var servicoId = UUID.randomUUID();
        var servicoEsperado = new Servico(servicoId, "Troca de Óleo", "Troca de óleo do motor", new BigDecimal("100.00"), 30, "Manutenção");

        when(servicoRepository.buscarPorId(servicoId)).thenReturn(Optional.of(servicoEsperado));

        // Act
        Servico servicoAtual = buscarServicoPorIdUseCase.executar(servicoId);

        // Assert
        assertNotNull(servicoAtual);
        assertEquals(servicoEsperado, servicoAtual);
    }

    @Test
    void deveLancarExcecaoQuandoServicoNaoEncontrado() {
        // Arrange
        var servicoId = UUID.randomUUID();
        when(servicoRepository.buscarPorId(servicoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ServicoNaoEncontradoException.class, () -> {
            buscarServicoPorIdUseCase.executar(servicoId);
        });
    }
}
