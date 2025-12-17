package br.com.alexsdm.postech.oficina.orcamento.core.usecase.impl;

import br.com.alexsdm.postech.oficina.orcamento.core.entity.Cliente;
import br.com.alexsdm.postech.oficina.orcamento.core.entity.Orcamento;
import br.com.alexsdm.postech.oficina.orcamento.core.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.orcamento.core.entity.Servico;
import br.com.alexsdm.postech.oficina.orcamento.core.exception.OrcamentoException;
import br.com.alexsdm.postech.oficina.orcamento.core.port.out.ClienteOrcamentoPort;
import br.com.alexsdm.postech.oficina.orcamento.core.port.out.OrcamentoPecaInsumoPort;
import br.com.alexsdm.postech.oficina.orcamento.core.port.out.OrcamentoRepository;
import br.com.alexsdm.postech.oficina.orcamento.core.port.out.OrcamentoServicoPort;
import br.com.alexsdm.postech.oficina.orcamento.core.usecase.input.CriarOrcamentoInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarOrcamentoUseCaseImplTest {

    @Mock
    private OrcamentoPecaInsumoPort pecaInsumoGateway;
    @Mock
    private OrcamentoServicoPort servicoGateway;
    @Mock
    private OrcamentoRepository orcamentoRepository;
    @Mock
    private ClienteOrcamentoPort clienteOrcamentoPort;

    @InjectMocks
    private CriarOrcamentoUseCaseImpl criarOrcamentoUseCase;

    private CriarOrcamentoInput input;
    private UUID clienteId;
    private UUID veiculoId;
    private UUID pecaId;
    private UUID servicoId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        veiculoId = UUID.randomUUID();
        pecaId = UUID.randomUUID();
        servicoId = UUID.randomUUID();

        var itemPecaInput = new CriarOrcamentoInput.CriarOrcamentoItemPecaInput(pecaId, 2);
        input = new CriarOrcamentoInput(clienteId.toString(), null, veiculoId, List.of(itemPecaInput), List.of(servicoId));
    }

    @Test
    void deveCriarOrcamentoComSucesso() {
        // Arrange
        var cliente = new Cliente(clienteId, "Cliente Teste", null, "12345678901", null, null, null, null, null);
        var peca = new PecaInsumo(pecaId, "Filtro", "Desc Peca", new BigDecimal("50.00"));
        var servico = new Servico(servicoId, "Troca de Oleo", new BigDecimal("100.00"), "Desc Servico");
        var orcamentoSalvo = new Orcamento(UUID.randomUUID(), clienteId, veiculoId, List.of(), List.of());

        when(clienteOrcamentoPort.buscarClienteComVeiculo(clienteId, veiculoId)).thenReturn(Optional.of(cliente));
        when(pecaInsumoGateway.buscarPecaParaOrcamentoPorId(pecaId)).thenReturn(Optional.of(peca));
        when(servicoGateway.buscarServicoParaOrcamentoPorId(servicoId)).thenReturn(Optional.of(servico));
        when(orcamentoRepository.salvar(any(Orcamento.class))).thenReturn(orcamentoSalvo);

        // Act
        UUID orcamentoId = criarOrcamentoUseCase.executar(input);

        // Assert
        assertNotNull(orcamentoId);
        ArgumentCaptor<Orcamento> orcamentoCaptor = ArgumentCaptor.forClass(Orcamento.class);
        verify(orcamentoRepository).salvar(orcamentoCaptor.capture());
        Orcamento orcamentoCriado = orcamentoCaptor.getValue();
        assertEquals(1, orcamentoCriado.getItensPeca().size());
        assertEquals(1, orcamentoCriado.getServicos().size());
        assertEquals(clienteId, orcamentoCriado.getClienteId());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(clienteOrcamentoPort.buscarClienteComVeiculo(clienteId, veiculoId)).thenReturn(Optional.empty());
        assertThrows(OrcamentoException.class, () -> criarOrcamentoUseCase.executar(input));
    }

    @Test
    void deveLancarExcecaoQuandoPecaNaoEncontrada() {
        var cliente = new Cliente(clienteId, "Nome", null, "123", null, null, null, null, null);
        when(clienteOrcamentoPort.buscarClienteComVeiculo(any(UUID.class), any(UUID.class))).thenReturn(Optional.of(cliente));
        when(pecaInsumoGateway.buscarPecaParaOrcamentoPorId(pecaId)).thenReturn(Optional.empty());
        assertThrows(OrcamentoException.class, () -> criarOrcamentoUseCase.executar(input));
    }

    @Test
    void deveLancarExcecaoQuandoServicoNaoEncontrado() {
        var cliente = new Cliente(clienteId, "Nome", null, "123", null, null, null, null, null);
        when(clienteOrcamentoPort.buscarClienteComVeiculo(any(UUID.class), any(UUID.class))).thenReturn(Optional.of(cliente));
        when(pecaInsumoGateway.buscarPecaParaOrcamentoPorId(pecaId)).thenReturn(Optional.of(new PecaInsumo(pecaId, "Peca", "Desc", BigDecimal.TEN)));
        when(servicoGateway.buscarServicoParaOrcamentoPorId(servicoId)).thenReturn(Optional.empty());
        assertThrows(OrcamentoException.class, () -> criarOrcamentoUseCase.executar(input));
    }

    @Test
    void deveLancarExcecaoQuandoNenhumIdentificadorClienteFornecido() {
        var inputSemCliente = new CriarOrcamentoInput(null, null, veiculoId, List.of(), List.of());
        assertThrows(IllegalArgumentException.class, () -> criarOrcamentoUseCase.executar(inputSemCliente));
    }
}
