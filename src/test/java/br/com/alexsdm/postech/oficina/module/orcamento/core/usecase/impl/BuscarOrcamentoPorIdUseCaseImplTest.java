package br.com.alexsdm.postech.oficina.module.orcamento.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.orcamento.core.entity.Cliente;
import br.com.alexsdm.postech.oficina.module.orcamento.core.entity.Orcamento;
import br.com.alexsdm.postech.oficina.module.orcamento.core.exception.OrcamentoException;
import br.com.alexsdm.postech.oficina.module.orcamento.core.exception.OrcamentoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.module.orcamento.core.port.out.ClienteOrcamentoPort;
import br.com.alexsdm.postech.oficina.module.orcamento.core.port.out.OrcamentoRepository;
import br.com.alexsdm.postech.oficina.module.orcamento.core.usecase.output.BuscarOrcamentoPorIdOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarOrcamentoPorIdUseCaseImplTest {

    @Mock
    private OrcamentoRepository orcamentoRepository;

    @Mock
    private ClienteOrcamentoPort clienteOrcamentoPort;

    @InjectMocks
    private BuscarOrcamentoPorIdUseCaseImpl buscarOrcamentoPorIdUseCase;

    @Test
    void deveBuscarOrcamentoPorIdComSucesso() {
        // Arrange
        var orcamentoId = UUID.randomUUID();
        var clienteId = UUID.randomUUID();
        var veiculoId = UUID.randomUUID();

        var orcamento = new Orcamento();
        orcamento.setId(orcamentoId);
        orcamento.setClienteId(clienteId);
        orcamento.setVeiculoId(veiculoId);

        var cliente = new Cliente(clienteId, "Nome", null, "123", null, null, null, null, null);
        var expectedOutput = new BuscarOrcamentoPorIdOutput(orcamentoId, null, null, null, null, null, null, null, null);

        when(orcamentoRepository.buscarPorId(orcamentoId)).thenReturn(Optional.of(orcamento));
        when(clienteOrcamentoPort.buscarClienteComVeiculo(clienteId, veiculoId)).thenReturn(Optional.of(cliente));

        try (MockedStatic<BuscarOrcamentoPorIdOutput> mockedStatic = Mockito.mockStatic(BuscarOrcamentoPorIdOutput.class)) {
            mockedStatic.when(() -> BuscarOrcamentoPorIdOutput.toOutput(orcamento, cliente)).thenReturn(expectedOutput);

            // Act
            BuscarOrcamentoPorIdOutput result = buscarOrcamentoPorIdUseCase.executar(orcamentoId);

            // Assert
            assertNotNull(result);
            assertEquals(expectedOutput, result);
        }
    }

    @Test
    void deveLancarExcecaoQuandoOrcamentoNaoEncontrado() {
        var orcamentoId = UUID.randomUUID();
        when(orcamentoRepository.buscarPorId(orcamentoId)).thenReturn(Optional.empty());
        assertThrows(OrcamentoNaoEncontradaException.class, () -> buscarOrcamentoPorIdUseCase.executar(orcamentoId));
    }

    @Test
    void deveLancarExcecaoQuandoClienteDoOrcamentoNaoEncontrado() {
        var orcamentoId = UUID.randomUUID();
        var orcamento = new Orcamento();
        orcamento.setId(orcamentoId);
        orcamento.setClienteId(UUID.randomUUID());
        orcamento.setVeiculoId(UUID.randomUUID());

        when(orcamentoRepository.buscarPorId(orcamentoId)).thenReturn(Optional.of(orcamento));
        when(clienteOrcamentoPort.buscarClienteComVeiculo(orcamento.getClienteId(), orcamento.getVeiculoId())).thenReturn(Optional.empty());

        assertThrows(OrcamentoException.class, () -> buscarOrcamentoPorIdUseCase.executar(orcamentoId));
    }
}
