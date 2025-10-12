package br.com.alexsdm.postech.oficina.module.cliente.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.OrdemServicoStatus;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteOrdemServicoPort;
import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.ListarStatusOsClienteInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarStatusOsClienteUseCaseImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteOrdemServicoPort clienteOrdemServicoPort;

    @InjectMocks
    private ListarStatusOsClienteUseCaseImpl listarStatusOsClienteUseCase;

    @Test
    void deveListarStatusOsClienteComSucesso() {
        var clienteId = UUID.randomUUID();
        var input = new ListarStatusOsClienteInput(clienteId);
        var cliente = Cliente.builder().id(clienteId).build();
        var status = new OrdemServicoStatus(UUID.randomUUID(), "EM_ANALISE");

        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));
        when(clienteOrdemServicoPort.buscarStatusPorCliente(clienteId)).thenReturn(Collections.singletonList(status));

        List<OrdemServicoStatus> result = listarStatusOsClienteUseCase.executar(input);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("EM_ANALISE", result.get(0).status());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontradoParaListarStatus() {
        var clienteId = UUID.randomUUID();
        var input = new ListarStatusOsClienteInput(clienteId);

        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            listarStatusOsClienteUseCase.executar(input);
        });
    }
}
