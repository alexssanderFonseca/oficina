package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.OrdemServico;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.Status;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.entity.Veiculo;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.exception.OrdemServicoClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.exception.OrdemServicoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.exception.OrdemServicoVeiculoNaoEncontradoException;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.out.OrdemServicoClientePort;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.port.out.OrdemServicoRepository;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.usecase.impl.BuscarOrdemServicoPorIdUseCaseImpl;
import br.com.alexsdm.postech.oficina.module.ordem_servico.core.usecase.output.BuscarOrdemServicoOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarOrdemServicoPorIdUseCaseImplTest {

    @Mock
    private OrdemServicoRepository gateway;
    @Mock
    private OrdemServicoClientePort ordemServicoClientePort;

    @InjectMocks
    private BuscarOrdemServicoPorIdUseCaseImpl buscarOrdemServicoPorIdUseCase;

    @Test
    void deveBuscarOrdemServicoPorIdComSucesso() {
        // Arrange
        var osId = UUID.randomUUID();
        var clienteId = UUID.randomUUID();
        var veiculoId = UUID.randomUUID();

        var veiculo = new Veiculo(veiculoId.toString(), "ABC-1234", "Marca", "Modelo", "2023", "Preto");
        var cliente = new Cliente(clienteId, null, "12345678901", List.of(veiculo));
        var os = OrdemServico.from(osId, clienteId, veiculoId, Collections.emptyList(), Collections.emptyList(), Status.EM_EXECUCAO, LocalDateTime.now(), null, null, null);

        when(gateway.buscarPorId(osId)).thenReturn(Optional.of(os));
        when(ordemServicoClientePort.buscarCliente(clienteId)).thenReturn(Optional.of(cliente));

        // Act
        BuscarOrdemServicoOutput result = buscarOrdemServicoPorIdUseCase.executar(osId);

        // Assert
        assertNotNull(result);
        assertEquals(osId, result.id());
        assertEquals("12345678901", result.dadosCliente().cpfCnpj());
        assertEquals("ABC-1234", result.dadosVeiculo().placa());
        assertEquals(Status.EM_EXECUCAO.toString(), result.status());
    }

    @Test
    void deveLancarExcecaoQuandoOrdemServicoNaoEncontrada() {
        var osId = UUID.randomUUID();
        when(gateway.buscarPorId(osId)).thenReturn(Optional.empty());
        assertThrows(OrdemServicoNaoEncontradaException.class, () -> buscarOrdemServicoPorIdUseCase.executar(osId));
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        var osId = UUID.randomUUID();
        var os = OrdemServico.from(osId, UUID.randomUUID(), UUID.randomUUID(), null, null, Status.EM_DIAGNOSTICO, null, null, null, null);
        when(gateway.buscarPorId(osId)).thenReturn(Optional.of(os));
        when(ordemServicoClientePort.buscarCliente(os.getClienteId())).thenReturn(Optional.empty());
        assertThrows(OrdemServicoClienteNaoEncontradoException.class, () -> buscarOrdemServicoPorIdUseCase.executar(osId));
    }

    @Test
    void deveLancarExcecaoQuandoVeiculoNaoEncontrado() {
        var osId = UUID.randomUUID();
        var clienteId = UUID.randomUUID();
        var veiculoId = UUID.randomUUID(); // Veiculo da OS
        var outroVeiculoId = UUID.randomUUID(); // Veiculo do cliente

        var veiculo = new Veiculo(outroVeiculoId.toString(), "DEF-5678", "Marca", "Modelo", "2023", "Preto");
        var cliente = new Cliente(clienteId, null, "12345678901", List.of(veiculo));
        var os = OrdemServico.from(osId, clienteId, veiculoId, null, null, Status.EM_DIAGNOSTICO, null, null, null, null);

        when(gateway.buscarPorId(osId)).thenReturn(Optional.of(os));
        when(ordemServicoClientePort.buscarCliente(clienteId)).thenReturn(Optional.of(cliente));

        assertThrows(OrdemServicoVeiculoNaoEncontradoException.class, () -> buscarOrdemServicoPorIdUseCase.executar(osId));
    }
}
