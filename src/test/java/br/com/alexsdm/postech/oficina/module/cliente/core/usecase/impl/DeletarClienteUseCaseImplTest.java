package br.com.alexsdm.postech.oficina.module.cliente.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.DeletarClienteInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarClienteUseCaseImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private DeletarClienteUseCaseImpl deletarClienteUseCase;

    @Test
    void deveDeletarClienteComSucesso() {
        var clienteId = UUID.randomUUID();
        var input = new DeletarClienteInput(clienteId);
        var cliente = Cliente.builder().id(clienteId).build();

        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).deletar(clienteId);

        deletarClienteUseCase.executar(input);

        verify(clienteRepository, times(1)).buscarPorId(clienteId);
        verify(clienteRepository, times(1)).deletar(clienteId);
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontradoParaDeletar() {
        var clienteId = UUID.randomUUID();
        var input = new DeletarClienteInput(clienteId);

        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            deletarClienteUseCase.executar(input);
        });

        verify(clienteRepository, never()).deletar(any(UUID.class));
    }
}
