package br.com.alexsdm.postech.oficina.servico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.servico.core.domain.entity.Servico;
import br.com.alexsdm.postech.oficina.servico.core.port.out.ServicoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarServicosUseCaseImplTest {

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private ListarServicosUseCaseImpl listarServicosUseCase;

    @Test
    void deveRetornarListaDeServicosQuandoExistirem() {
        // Arrange
        var servico1 = new Servico(UUID.randomUUID(), "Troca de Óleo", "Troca de óleo do motor", new BigDecimal("100.00"), 30, "Manutenção");
        var servico2 = new Servico(UUID.randomUUID(), "Alinhamento", "Alinhamento e balanceamento", new BigDecimal("150.00"), 60, "Manutenção");
        var servicosEsperados = List.of(servico1, servico2);

        when(servicoRepository.listarTodos()).thenReturn(servicosEsperados);

        // Act
        List<Servico> servicosAtuais = listarServicosUseCase.executar();

        // Assert
        assertNotNull(servicosAtuais);
        assertFalse(servicosAtuais.isEmpty());
        assertEquals(2, servicosAtuais.size());
        assertEquals(servicosEsperados, servicosAtuais);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremServicos() {
        // Arrange
        when(servicoRepository.listarTodos()).thenReturn(Collections.emptyList());

        // Act
        List<Servico> servicosAtuais = listarServicosUseCase.executar();

        // Assert
        assertNotNull(servicosAtuais);
        assertTrue(servicosAtuais.isEmpty());
    }
}
