package br.com.alexsdm.postech.oficina.cliente.application.usecase.impl;

import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.impl.AdicionarVeiculoUseCaseImpl;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.AdicionarVeiculoInput;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Endereco;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.exception.ClienteNaoEncontradoException;
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
    private ClienteRepository clienteRepository;

    @InjectMocks
    private AdicionarVeiculoUseCaseImpl adicionarVeiculoUseCase;

    private Cliente cliente;
    private AdicionarVeiculoInput input;
    private UUID clienteId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();


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


        input = AdicionarVeiculoInput.builder()
                .clienteId(clienteId)
                .modelo("Corsa")
                .marca("Chevrolet")
                .placa("NET-5747")
                .ano("2020")
                .cor("Azul")
                .build();
    }

    @Test
    @DisplayName("Deve adicionar veículo ao cliente com sucesso")
    void deveAdicionarVeiculoComSucesso() {
        // Arrange
        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));
        when(clienteRepository.salvar(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        var output = adicionarVeiculoUseCase.executar(input);

        // Assert
        assertNotNull(output);
        assertNotNull(output.veiculoId());
        verify(clienteRepository).buscarPorId(clienteId);
        verify(clienteRepository).salvar(any(Cliente.class));
        assertEquals(1, cliente.getVeiculos().size());

        assertEquals("NET-5747", cliente.getVeiculos().getFirst().getPlaca());
    }

    @Test
    @DisplayName("Deve lançar ClienteNaoEncontradoException quando cliente não existe")
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        // Arrange
        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ClienteNaoEncontradoException.class, () -> {
            adicionarVeiculoUseCase.executar(input);
        });
        verify(clienteRepository).buscarPorId(clienteId);
        verify(clienteRepository, never()).salvar(any());
    }

}
