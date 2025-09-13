package br.com.alexsdm.postech.oficina.cliente.application.usecase.impl;

import br.com.alexsdm.postech.oficina.cliente.application.gateway.ClienteGateway;
import br.com.alexsdm.postech.oficina.cliente.application.gateway.OrdemServicoGateway;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.ListarStatusOsClienteInput;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.OrdemServicoStatus;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteNaoEncontradoException;
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
    private ClienteGateway clienteGateway;

    @Mock
    private OrdemServicoGateway ordemServicoGateway;

    private Cliente cliente;

    @InjectMocks
    private ListarStatusOsClienteUseCaseImpl listarStatusOsClienteUseCase;

    @BeforeEach
    void setup() {
        this.cliente = mock();
    }

    @Test
    @DisplayName("Deve retornar lista de status de OS quando cliente existe")
    void deveRetornarListaDeStatusQuandoClienteExiste() {
        // Arrange
        var clienteId = UUID.randomUUID();
        var input = new ListarStatusOsClienteInput(clienteId);
        var listaStatus = List.of(new OrdemServicoStatus(1L, "EM_DIAGNOSTICO"));

        when(clienteGateway.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));
        when(ordemServicoGateway.buscarStatusPorCliente(clienteId)).thenReturn(listaStatus);

        // Act
        var resultado = listarStatusOsClienteUseCase.executar(input);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("EM_DIAGNOSTICO", resultado.get(0).status());
        verify(clienteGateway).buscarPorId(clienteId);
        verify(ordemServicoGateway).buscarStatusPorCliente(clienteId);
    }

    @Test
    @DisplayName("Deve lançar ClienteNaoEncontradoException quando cliente não existe")
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        // Arrange
        var clienteId = UUID.randomUUID();
        var input = new ListarStatusOsClienteInput(clienteId);

        when(clienteGateway.buscarPorId(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ClienteNaoEncontradoException.class, () -> {
            listarStatusOsClienteUseCase.executar(input);
        });

        verify(clienteGateway).buscarPorId(clienteId);
        verify(ordemServicoGateway, never()).buscarStatusPorCliente(any());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando cliente existe mas não tem ordens de serviço")
    void deveRetornarListaVaziaQuandoClienteNaoTemOS() {
        // Arrange
        var clienteId = UUID.randomUUID();
        var input = new ListarStatusOsClienteInput(clienteId);
        var clienteOptional = Optional.ofNullable(cliente);
        var cliente =
                when(clienteGateway.buscarPorId(clienteId)).thenReturn(clienteOptional);
        when(ordemServicoGateway.buscarStatusPorCliente(clienteId)).thenReturn(List.of());

        // Act
        var resultado = listarStatusOsClienteUseCase.executar(input);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(clienteGateway).buscarPorId(clienteId);
        verify(ordemServicoGateway).buscarStatusPorCliente(clienteId);
    }
}
