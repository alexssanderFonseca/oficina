package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.OrdemServico;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.Status;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.out.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.usecase.impl.ListarOrdensServicoConcluidasUseCaseImpl;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.usecase.output.ListarOrdensServicoConcluidasUseCaseOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarOrdensServicoConcluidasUseCaseImplTest {

    @Mock
    private OrdemServicoRepository repository;

    @InjectMocks
    private ListarOrdensServicoConcluidasUseCaseImpl listarOrdensServicoConcluidasUseCase;

    @Test
    void deveListarOrdensDeServicoConcluidasComSucesso() {
        // Arrange
        var os = OrdemServico.from(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), null, null, Status.FINALIZADA, LocalDateTime.now(), LocalDateTime.now(), null, LocalDateTime.now());
        var listaOs = List.of(os);

        when(repository.buscarFinalizadas()).thenReturn(listaOs);

        // Act
        List<ListarOrdensServicoConcluidasUseCaseOutput> result = listarOrdensServicoConcluidasUseCase.executar();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(os.getId(), result.get(0).osId());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverOrdensConcluidas() {
        // Arrange
        when(repository.buscarFinalizadas()).thenReturn(Collections.emptyList());

        // Act
        List<ListarOrdensServicoConcluidasUseCaseOutput> result = listarOrdensServicoConcluidasUseCase.executar();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
