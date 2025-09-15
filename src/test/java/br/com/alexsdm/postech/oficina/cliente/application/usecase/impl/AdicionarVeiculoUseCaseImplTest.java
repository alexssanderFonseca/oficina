package br.com.alexsdm.postech.oficina.cliente.application.usecase.impl;

import br.com.alexsdm.postech.oficina.cliente.application.gateway.ClienteGateway;
import br.com.alexsdm.postech.oficina.cliente.application.gateway.ClienteVeiculoModeloGateway;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.AdicionarVeiculoInput;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.Endereco;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.VeiculoModelo;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.cliente.exception.VeiculoModeloNaoEncontradoException;
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
@DisplayName("Testes para AdicionarVeiculoUseCaseImpl")
class AdicionarVeiculoUseCaseImplTest {

    @Mock
    private ClienteGateway clienteGateway;

    @Mock
    private ClienteVeiculoModeloGateway veiculoModeloGateway;

    @InjectMocks
    private AdicionarVeiculoUseCaseImpl adicionarVeiculoUseCase;

    private Cliente cliente;
    private VeiculoModelo veiculoModelo;
    private AdicionarVeiculoInput input;
    private UUID clienteId;
    private Long veiculoModeloId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        veiculoModeloId = 1L;

        var endereco = Endereco.builder()
                .id(UUID.randomUUID())
                .rua("Rua Teste")
                .numero("123")
                .bairro("Bairro Teste")
                .cep("12345-678")
                .cidade("Cidade Teste")
                .uf("TS")
                .build();

        cliente = Cliente.builder()
                .id(clienteId)
                .nome("John")
                .sobrenome("Doe")
                .cpfCnpj("12345678901")
                .email("john.doe@example.com")
                .telefone("11999999999")
                .endereco(endereco)
                .build();

        veiculoModelo = new VeiculoModelo(1L, "Marca Teste", "Modelo Teste");

        input = AdicionarVeiculoInput.builder()
                .clienteId(clienteId)
                .veiculoModeloId(veiculoModeloId)
                .placa("NET-5747")
                .ano("2020")
                .cor("Azul")
                .build();
    }

    @Test
    @DisplayName("Deve adicionar veículo ao cliente com sucesso")
    void deveAdicionarVeiculoComSucesso() {
        // Arrange
        when(clienteGateway.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));
        when(veiculoModeloGateway.buscarPorId(veiculoModeloId)).thenReturn(Optional.of(veiculoModelo));
        when(clienteGateway.salvar(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        var output = adicionarVeiculoUseCase.executar(input);

        // Assert
        assertNotNull(output);
        assertNotNull(output.veiculoId());
        verify(clienteGateway).buscarPorId(clienteId);
        verify(veiculoModeloGateway).buscarPorId(veiculoModeloId);
        verify(clienteGateway).salvar(any(Cliente.class));
        assertEquals(1, cliente.getVeiculos().size());
        veiculoModelo = new VeiculoModelo(1L, "Marca Teste", "Modelo Teste");
        // ...
        assertEquals("NET-5747", cliente.getVeiculos().getFirst().getPlaca());
    }

    @Test
    @DisplayName("Deve lançar ClienteNaoEncontradoException quando cliente não existe")
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        // Arrange
        when(clienteGateway.buscarPorId(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ClienteNaoEncontradoException.class, () -> {
            adicionarVeiculoUseCase.executar(input);
        });
        verify(clienteGateway).buscarPorId(clienteId);
        verify(veiculoModeloGateway, never()).buscarPorId(any());
        verify(clienteGateway, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar VeiculoModeloNaoEncontradoException quando modelo de veículo não existe")
    void deveLancarExcecaoQuandoVeiculoModeloNaoExiste() {
        // Arrange
        when(clienteGateway.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));
        when(veiculoModeloGateway.buscarPorId(veiculoModeloId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(VeiculoModeloNaoEncontradoException.class, () -> {
            adicionarVeiculoUseCase.executar(input);
        });
        verify(clienteGateway).buscarPorId(clienteId);
        verify(veiculoModeloGateway).buscarPorId(veiculoModeloId);
        verify(clienteGateway, never()).salvar(any());
    }
}
