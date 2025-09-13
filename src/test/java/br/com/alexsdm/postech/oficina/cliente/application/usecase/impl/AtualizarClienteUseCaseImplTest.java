package br.com.alexsdm.postech.oficina.cliente.application.usecase.impl;

import br.com.alexsdm.postech.oficina.cliente.application.gateway.ClienteGateway;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.AtualizarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.EnderecoInput;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.mapper.ClienteDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para AtualizarClienteUseCaseImpl")
class AtualizarClienteUseCaseImplTest {

    @Mock
    private ClienteGateway clienteGateway;

    @Mock
    private ClienteDTOMapper clienteDTOMapper;

    @InjectMocks
    private AtualizarClienteUseCaseImpl atualizarClienteUseCase;

    private Cliente cliente;
    private UUID clienteId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        cliente = new Cliente(clienteId, "Nome", "Antigo", "163.957.080-23", "email.antigo@teste.com", null, "123456789");
    }

    @Test
    @DisplayName("Deve atualizar email e telefone do cliente com sucesso")
    void deveAtualizarEmailETelefoneComSucesso() {
        // Arrange
        var input = AtualizarClienteInput.builder()
                .clienteId(clienteId)
                .email("email.novo@teste.com")
                .telefone("987654321")
                .build();

        when(clienteGateway.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));
        when(clienteGateway.salvar(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        atualizarClienteUseCase.executar(input);

        // Assert
        verify(clienteGateway).salvar(any(Cliente.class));
        assertEquals("email.novo@teste.com", cliente.getEmail());
        assertEquals("987654321", cliente.getTelefone());
    }

    @Test
    @DisplayName("Deve atualizar apenas os campos não nulos")
    void deveAtualizarApenasCamposNaoNulos() {
        // Arrange
        var input = AtualizarClienteInput.builder()
                .clienteId(clienteId)
                .email("email.novo.parcial@teste.com")
                .build();

        when(clienteGateway.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));
        when(clienteGateway.salvar(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        atualizarClienteUseCase.executar(input);

        // Assert
        verify(clienteGateway).salvar(any(Cliente.class));
        assertEquals("email.novo.parcial@teste.com", cliente.getEmail());
        assertEquals("123456789", cliente.getTelefone()); // Telefone não deve mudar
    }
    
    @Test
    @DisplayName("Deve atualizar o endereço do cliente com sucesso")
    void deveAtualizarEnderecoComSucesso() {
        // Arrange
        var enderecoInput = EnderecoInput.builder().rua("Rua Nova").build();
        var input = AtualizarClienteInput.builder()
                .clienteId(clienteId)
                .endereco(enderecoInput)
                .build();

        when(clienteGateway.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));
        when(clienteGateway.salvar(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        atualizarClienteUseCase.executar(input);

        // Assert
        verify(clienteGateway).salvar(any(Cliente.class));
        assertNotNull(cliente.getEndereco());
        assertEquals("Rua Nova", cliente.getEndereco().rua());
    }

    @Test
    @DisplayName("Deve lançar ClienteNaoEncontradoException quando cliente não existe")
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        // Arrange
        var input = AtualizarClienteInput.builder().clienteId(clienteId).build();
        when(clienteGateway.buscarPorId(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ClienteNaoEncontradoException.class, () -> {
            atualizarClienteUseCase.executar(input);
        });
        verify(clienteGateway, never()).salvar(any());
    }
}
