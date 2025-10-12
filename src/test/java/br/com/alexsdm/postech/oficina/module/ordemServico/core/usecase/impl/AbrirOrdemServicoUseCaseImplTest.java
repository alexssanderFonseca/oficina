package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.OrdemServico;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.Servico;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.Status;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.out.OrdemServicoPecaInsumoPort;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.out.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.out.OrdemServicoServicoPort;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.usecase.impl.AbrirOrdemServicoUseCaseImpl;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.usecase.input.CriarOrdemServicoInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbrirOrdemServicoUseCaseImplTest {

    @Mock
    private OrdemServicoRepository ordemServicoRepository;
    @Mock
    private OrdemServicoPecaInsumoPort ordemServicoPecaPort;
    @Mock
    private OrdemServicoServicoPort ordemServicoServicoPort;

    @InjectMocks
    private AbrirOrdemServicoUseCaseImpl abrirOrdemServicoUseCase;

    @Test
    void deveAbrirOrdemServicoParaExecucaoComSucesso() {
        // Arrange
        var clienteId = UUID.randomUUID();
        var veiculoId = UUID.randomUUID();
        var pecaId = UUID.randomUUID();
        var servicoId = UUID.randomUUID();
        var itemPecaInput = new CriarOrdemServicoInput.CriarOrdemServicoItemInsumoInput(pecaId, 1);
        var input = new CriarOrdemServicoInput(clienteId, veiculoId, List.of(itemPecaInput), List.of(servicoId));

        var peca = new PecaInsumo(pecaId, "Peca Teste", "Desc Peca", 1, BigDecimal.TEN);
        var servico = new Servico(servicoId, "Servico Teste", "Desc Servico", BigDecimal.ONE);

        when(ordemServicoPecaPort.buscarPecaInsumo(pecaId)).thenReturn(Optional.of(peca));
        when(ordemServicoServicoPort.buscarServicoPorId(servicoId)).thenReturn(Optional.of(servico));

        // Act
        UUID osId = abrirOrdemServicoUseCase.executar(input);

        // Assert
        assertNotNull(osId);
        ArgumentCaptor<OrdemServico> osCaptor = ArgumentCaptor.forClass(OrdemServico.class);
        verify(ordemServicoRepository).salvar(osCaptor.capture());
        OrdemServico osSalva = osCaptor.getValue();

        assertEquals(Status.EM_EXECUCAO, osSalva.getStatus());
        assertEquals(1, osSalva.getItensPecaOrdemServico().size());
        assertEquals(1, osSalva.getServicos().size());
    }

    @Test
    void deveAbrirOrdemServicoParaDiagnosticoQuandoNaoHouverItens() {
        // Arrange
        var clienteId = UUID.randomUUID();
        var veiculoId = UUID.randomUUID();
        var input = new CriarOrdemServicoInput(clienteId, veiculoId, Collections.emptyList(), Collections.emptyList());

        // Act
        UUID osId = abrirOrdemServicoUseCase.executar(input);

        // Assert
        assertNotNull(osId);
        ArgumentCaptor<OrdemServico> osCaptor = ArgumentCaptor.forClass(OrdemServico.class);
        verify(ordemServicoRepository).salvar(osCaptor.capture());
        OrdemServico osSalva = osCaptor.getValue();

        assertEquals(Status.EM_DIAGNOSTICO, osSalva.getStatus());
        verify(ordemServicoPecaPort, never()).buscarPecaInsumo(any());
        verify(ordemServicoServicoPort, never()).buscarServicoPorId(any());
    }
}
