package br.com.alexsdm.postech.oficina.cliente.application.usecase.impl;

import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteOrdemServicoPort;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.impl.ListarStatusOsClienteUseCaseImpl;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.ListarStatusOsClienteInput;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.OrdemServicoStatus;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.exception.ClienteNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para ListarStatusOsClienteUseCaseImpl")
class ListarStatusOsClienteUseCaseImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteOrdemServicoPort ordemServicoGateway;

    private Cliente cliente;

    @InjectMocks
    private ListarStatusOsClienteUseCaseImpl listarStatusOsClienteUseCase;

    @BeforeEach
    void setup() {
        this.cliente = mock(Cliente.class);
    }

    @Test
    @DisplayName("Deve retornar lista de status de OS quando cliente existe")
    void deveRetornarListaDeStatusQuandoClienteExiste() {
        var clienteId = UUID.randomUUID();
        var input = new ListarStatusOsClienteInput(clienteId);
        var listaStatus = List.of(new OrdemServicoStatus(UUID.randomUUID(), "EM_DIAGNOSTICO"));

        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));
        when(ordemServicoGateway.buscarStatusPorCliente(clienteId)).thenReturn(listaStatus);

        var resultado = listarStatusOsClienteUseCase.executar(input);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("EM_DIAGNOSTICO", resultado.get(0).status());
        verify(clienteRepository).buscarPorId(clienteId);
        verify(ordemServicoGateway).buscarStatusPorCliente(clienteId);
    }

    @Test
    @DisplayName("Deve lançar ClienteNaoEncontradoException quando cliente não existe")
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        var clienteId = UUID.randomUUID();
        var input = new ListarStatusOsClienteInput(clienteId);

        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            listarStatusOsClienteUseCase.executar(input);
        });

        verify(clienteRepository).buscarPorId(clienteId);
        verify(ordemServicoGateway, never()).buscarStatusPorCliente(any());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando cliente existe mas não tem ordens de serviço")
    void deveRetornarListaVaziaQuandoClienteNaoTemOS() {
        var clienteId = UUID.randomUUID();
        var input = new ListarStatusOsClienteInput(clienteId);

        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));
        when(ordemServicoGateway.buscarStatusPorCliente(clienteId)).thenReturn(List.of());

        var resultado = listarStatusOsClienteUseCase.executar(input);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(clienteRepository).buscarPorId(clienteId);
        verify(ordemServicoGateway).buscarStatusPorCliente(clienteId);
    }
}