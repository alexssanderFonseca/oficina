package br.com.alexsdm.postech.oficina.module.cliente.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.cliente.adapters.in.controller.mapper.ClienteDTOMapper;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Endereco;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.AtualizarClienteInput;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.EnderecoInput;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.output.BuscarClientePorIdOutput;
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
class AtualizarClienteUseCaseImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteDTOMapper clienteDTOMapper;

    @InjectMocks
    private AtualizarClienteUseCaseImpl atualizarClienteUseCase;

    private AtualizarClienteInput input;
    private Cliente clienteExistente;
    private UUID clienteId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        var enderecoInput = new EnderecoInput("Nova Rua", "456", "Novo Bairro", "98765-432", "Nova Cidade", "NV");
        input = new AtualizarClienteInput(
                clienteId,
                "novo.email@teste.com",
                "11999998888",
                enderecoInput
        );

        clienteExistente = Cliente.builder()
                .id(clienteId)
                .nome("Cliente")
                .sobrenome("Existente")
                .email("antigo.email@teste.com")
                .telefone("11988887777")
                .endereco(Endereco.builder().rua("Rua Antiga").build())
                .build();
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.salvar(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(clienteDTOMapper.toOutput(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente cliente = invocation.getArgument(0);
            return BuscarClientePorIdOutput.builder()
                    .id(cliente.getId())
                    .email(cliente.getEmail())
                    .telefone(cliente.getTelefone())
                    .build();
        });

        var output = atualizarClienteUseCase.executar(input);

        assertNotNull(output);
        assertEquals(clienteId, output.id());
        assertEquals("novo.email@teste.com", output.email());
        assertEquals("11999998888", output.telefone());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            atualizarClienteUseCase.executar(input);
        });
    }
}
