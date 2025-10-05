package br.com.alexsdm.postech.oficina.cliente.application.usecase.impl;

import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.impl.CadastrarClienteUseCaseImpl;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.CadastrarClienteInput;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.EnderecoInput;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Endereco;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.exception.ClienteException;
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
@DisplayName("Testes para CadastrarClienteUseCaseImpl")
class CadastrarClienteUseCaseImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private CadastrarClienteUseCaseImpl cadastrarClienteUseCase;

    private CadastrarClienteInput cadastrarClienteInput;
    private Cliente clienteSalvo;
    private String cpfCnpj = "12345678901";

    @BeforeEach
    void setUp() {
        var enderecoInput = EnderecoInput.builder()
                .rua("Rua Teste")
                .numero("123")
                .bairro("Bairro Teste")
                .cep("12345-678")
                .cidade("Cidade Teste")
                .uf("TS")
                .build();

        cadastrarClienteInput = CadastrarClienteInput.builder()
                .nome("John")
                .sobrenome("Doe")
                .cpfCnpj(cpfCnpj)
                .email("john.doe@example.com")
                .telefone("11999999999")
                .endereco(enderecoInput)
                .build();

        var endereco = Endereco.builder()
                .id(UUID.randomUUID())
                .rua("Rua Teste")
                .numero("123")
                .bairro("Bairro Teste")
                .cep("12345-678")
                .cidade("Cidade Teste")
                .uf("TS")
                .build();

        clienteSalvo = Cliente.builder()
                .id(UUID.randomUUID())
                .nome("John")
                .sobrenome("Doe")
                .cpfCnpj(cpfCnpj)
                .email("john.doe@example.com")
                .telefone("11999999999")
                .endereco(endereco)
                .build();
    }

    @Test
    @DisplayName("Deve cadastrar cliente com sucesso quando não existir um com o mesmo documento")
    void deveCadastrarClienteComSucesso() {
        // Arrange
        when(clienteRepository.buscarPorDocumento(cpfCnpj)).thenReturn(Optional.empty());
        when(clienteRepository.salvar(any(Cliente.class))).thenReturn(clienteSalvo);

        // Act
        var output = cadastrarClienteUseCase.executar(cadastrarClienteInput);

        // Assert
        assertNotNull(output);
        assertEquals(clienteSalvo.getId(), output.id());
        verify(clienteRepository).buscarPorDocumento(cpfCnpj);
        verify(clienteRepository).salvar(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar ClienteException ao tentar cadastrar cliente com documento que já existe")
    void deveLancarExcecaoAoCadastrarClienteComDocumentoExistente() {
        // Arrange
        when(clienteRepository.buscarPorDocumento(cpfCnpj)).thenReturn(Optional.of(clienteSalvo));

        // Act & Assert
        var exception = assertThrows(ClienteException.class, () -> {
            cadastrarClienteUseCase.executar(cadastrarClienteInput);
        });

        assertEquals("Cliente já cadastrado!", exception.getMessage());
        verify(clienteRepository).buscarPorDocumento(cpfCnpj);
        verify(clienteRepository, never()).salvar(any(Cliente.class));
    }
}
