package br.com.alexsdm.postech.oficina.cliente.core.usecase.impl;

import br.com.alexsdm.postech.oficina.cliente.core.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.core.domain.exception.ClienteException;
import br.com.alexsdm.postech.oficina.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.input.CadastrarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.input.EnderecoInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarClienteUseCaseImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private CadastrarClienteUseCaseImpl cadastrarClienteUseCase;

    private CadastrarClienteInput input;

    @BeforeEach
    void setUp() {
        var enderecoInput = new EnderecoInput("Rua Teste", "123", "Bairro Teste", "12345-678", "Cidade Teste", "TS");
        input = new CadastrarClienteInput(
                "Cliente",
                "Teste",
                "12345678901",
                "cliente.teste@email.com",
                "11987654321",
                enderecoInput
        );
    }

    @Test
    void deveCadastrarClienteComSucesso() {
        when(clienteRepository.buscarPorDocumento(input.cpfCnpj())).thenReturn(Optional.empty());
        when(clienteRepository.salvar(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente cliente = invocation.getArgument(0);
            return Cliente.builder()
                    .id(UUID.randomUUID())
                    .nome(cliente.getNome())
                    .sobrenome(cliente.getSobrenome())
                    .cpfCnpj(cliente.getCpfCnpj())
                    .email(cliente.getEmail())
                    .telefone(cliente.getTelefone())
                    .endereco(cliente.getEndereco())
                    .build();
        });

        var output = cadastrarClienteUseCase.executar(input);

        assertNotNull(output);
        assertNotNull(output.id());
    }

    @Test
    void deveLancarExcecaoQuandoClienteJaCadastrado() {
        var clienteExistente = Cliente.builder()
                .id(UUID.randomUUID())
                .nome("Existente")
                .sobrenome("Cliente")
                .cpfCnpj(input.cpfCnpj())
                .build();
        when(clienteRepository.buscarPorDocumento(input.cpfCnpj())).thenReturn(Optional.of(clienteExistente));

        var exception = assertThrows(ClienteException.class, () -> {
            cadastrarClienteUseCase.executar(input);
        });

        assertEquals("Cliente já cadastrado!", exception.getMessage());
    }
}
