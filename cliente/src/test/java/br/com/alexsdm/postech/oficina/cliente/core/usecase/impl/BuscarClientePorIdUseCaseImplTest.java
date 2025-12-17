package br.com.alexsdm.postech.oficina.cliente.core.usecase.impl;

import br.com.alexsdm.postech.oficina.cliente.core.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.core.domain.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.output.BuscarClientePorIdOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarClientePorIdUseCaseImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private BuscarClientePorIdUseCaseImpl buscarClientePorIdUseCase;

    @Test
    void deveBuscarClientePorIdComSucesso() {
        var clienteId = UUID.randomUUID();
        var cliente = Cliente.builder()
                .id(clienteId)
                .nome("Cliente")
                .build();

        var expectedOutput = BuscarClientePorIdOutput.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .build();

        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));

        try (MockedStatic<BuscarClientePorIdOutput> mockedStatic = Mockito.mockStatic(BuscarClientePorIdOutput.class)) {
            mockedStatic.when(() -> BuscarClientePorIdOutput.toOutput(cliente)).thenReturn(expectedOutput);

            var output = buscarClientePorIdUseCase.executar(clienteId);

            assertNotNull(output);
            assertEquals(cliente.getId(), output.id());
            assertEquals(cliente.getNome(), output.nome());
        }
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontradoPorId() {
        var clienteId = UUID.randomUUID();
        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            buscarClientePorIdUseCase.executar(clienteId);
        });
    }
}
