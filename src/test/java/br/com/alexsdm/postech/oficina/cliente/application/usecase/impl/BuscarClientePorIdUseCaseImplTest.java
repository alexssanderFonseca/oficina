package br.com.alexsdm.postech.oficina.cliente.application.usecase.impl;

import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.impl.BuscarClientePorIdUseCaseImpl;
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

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para BuscarClientePorIdUseCaseImpl")
class BuscarClientePorIdUseCaseImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private BuscarClientePorIdUseCaseImpl buscarClientePorIdUseCase;

    private Cliente cliente;
    private UUID clienteId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        var endereco = Endereco.builder()
                .id(UUID.randomUUID())
                .rua("Rua Exemplo")
                .numero("456")
                .bairro("Bairro Exemplo")
                .cep("87654-321")
                .cidade("Cidade Exemplo")
                .uf("EX")
                .build();

        cliente = new Cliente(
                clienteId,
                "Jane",
                "Doe",
                "98765432109",
                "jane.doe@example.com",
                endereco,
                "11888888888",
                new ArrayList<>()
        );
    }

    @Test
    @DisplayName("Deve buscar e retornar cliente com sucesso quando o ID existe")
    void deveRetornarClienteComSucesso() {
        // Arrange
        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));

        // Act
        var output = buscarClientePorIdUseCase.executar(clienteId);

        // Assert
        assertNotNull(output);
        assertEquals(cliente.getId(), output.id());
        assertEquals(cliente.getNome(), output.nome());
        assertEquals(cliente.getCpfCnpj(), output.cpfCnpj());
        assertEquals(cliente.getEndereco().rua(), output.endereco().rua());
        assertTrue(output.veiculos().isEmpty());
        verify(clienteRepository).buscarPorId(clienteId);
    }

    @Test
    @DisplayName("Deve lançar ClienteNaoEncontradoException quando o ID não existe")
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        // Arrange
        var idInexistente = UUID.randomUUID();

        when(clienteRepository.buscarPorId(idInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(ClienteNaoEncontradoException.class, () -> {
            buscarClientePorIdUseCase.executar(idInexistente);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(clienteRepository).buscarPorId(idInexistente);
    }
}
