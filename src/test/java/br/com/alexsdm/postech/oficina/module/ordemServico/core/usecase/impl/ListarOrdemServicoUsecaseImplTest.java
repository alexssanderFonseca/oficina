package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.OrdemServico;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.Status;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.pagination.Page;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.out.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.usecase.impl.ListarOrdemServicoUsecaseImpl;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.usecase.output.ListarOrdemServicoUsecaseOutput;
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
class ListarOrdemServicoUsecaseImplTest {

    @Mock
    private OrdemServicoRepository repository;

    @InjectMocks
    private ListarOrdemServicoUsecaseImpl listarOrdemServicoUsecase;

    @Test
    void deveListarOrdensDeServicoComSucesso() {
        // Arrange
        var os = OrdemServico.from(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), null, null, Status.EM_EXECUCAO, LocalDateTime.now(), null, null, null);
        var pageRequest = new Page<>(List.of(os), 1, 1, 0);

        when(repository.listarTodasOrdenadas(0L, 10L)).thenReturn(pageRequest);

        // Act
        Page<ListarOrdemServicoUsecaseOutput> resultPage = listarOrdemServicoUsecase.executar(0L, 10L);

        // Assert
        assertNotNull(resultPage);
        assertEquals(1, resultPage.totalPaginas());
        assertEquals(0, resultPage.pagina());
        assertFalse(resultPage.conteudo().isEmpty());
        assertEquals(1, resultPage.conteudo().size());
        assertEquals(os.getId(), resultPage.conteudo().get(0).osId());
    }

    @Test
    void deveRetornarPaginaVaziaQuandoNaoHouverOrdensDeServico() {
        // Arrange
        var pageRequest = new Page<OrdemServico>(Collections.emptyList(), 0, 0, 0);
        when(repository.listarTodasOrdenadas(0L, 10L)).thenReturn(pageRequest);

        // Act
        Page<ListarOrdemServicoUsecaseOutput> resultPage = listarOrdemServicoUsecase.executar(0L, 10L);

        // Assert
        assertNotNull(resultPage);
        assertEquals(0, resultPage.totalPaginas());
        assertTrue(resultPage.conteudo().isEmpty());
    }
}
