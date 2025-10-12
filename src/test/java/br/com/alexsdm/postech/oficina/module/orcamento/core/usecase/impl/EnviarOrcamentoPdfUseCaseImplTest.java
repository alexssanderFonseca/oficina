package br.com.alexsdm.postech.oficina.module.orcamento.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.orcamento.core.entity.Cliente;
import br.com.alexsdm.postech.oficina.module.orcamento.core.entity.Orcamento;
import br.com.alexsdm.postech.oficina.module.orcamento.core.exception.OrcamentoException;
import br.com.alexsdm.postech.oficina.module.orcamento.core.exception.OrcamentoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.module.orcamento.core.port.out.ClienteOrcamentoPort;
import br.com.alexsdm.postech.oficina.module.orcamento.core.port.out.OrcamentoRepository;
import br.com.alexsdm.postech.oficina.module.orcamento.core.service.PdfGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnviarOrcamentoPdfUseCaseImplTest {

    @Mock
    private OrcamentoRepository orcamentoRepository;
    @Mock
    private ClienteOrcamentoPort clienteOrcamentoPort;
    @Mock
    private PdfGenerator pdfGenerator;

    @InjectMocks
    private EnviarOrcamentoPdfUseCaseImpl enviarOrcamentoPdfUseCase;

    @Test
    void deveGerarPdfComSucesso() {
        // Arrange
        var orcamentoId = UUID.randomUUID();
        var clienteId = UUID.randomUUID();
        var veiculoId = UUID.randomUUID();
        var orcamento = new Orcamento(orcamentoId, clienteId, veiculoId, Collections.emptyList(), Collections.emptyList());
        var cliente = new Cliente(clienteId, "Nome", null, "123", null, null, null, null, null);
        var pdfBytes = new byte[]{1, 2, 3};

        when(orcamentoRepository.buscarPorId(orcamentoId)).thenReturn(Optional.of(orcamento));
        when(clienteOrcamentoPort.buscarClienteComVeiculo(clienteId, veiculoId)).thenReturn(Optional.of(cliente));
        when(pdfGenerator.generate(any(Orcamento.class), any(Cliente.class))).thenReturn(pdfBytes);

        // Act
        byte[] result = enviarOrcamentoPdfUseCase.executar(orcamentoId);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.length);
    }

    @Test
    void deveLancarExcecaoQuandoOrcamentoNaoEncontradoParaEnviarPdf() {
        var orcamentoId = UUID.randomUUID();
        when(orcamentoRepository.buscarPorId(orcamentoId)).thenReturn(Optional.empty());
        assertThrows(OrcamentoNaoEncontradaException.class, () -> enviarOrcamentoPdfUseCase.executar(orcamentoId));
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontradoParaEnviarPdf() {
        var orcamentoId = UUID.randomUUID();
        var orcamento = new Orcamento(orcamentoId, UUID.randomUUID(), UUID.randomUUID(), Collections.emptyList(), Collections.emptyList());

        when(orcamentoRepository.buscarPorId(orcamentoId)).thenReturn(Optional.of(orcamento));
        when(clienteOrcamentoPort.buscarClienteComVeiculo(orcamento.getClienteId(), orcamento.getVeiculoId())).thenReturn(Optional.empty());

        assertThrows(OrcamentoException.class, () -> enviarOrcamentoPdfUseCase.executar(orcamentoId));
    }
}
