package br.com.alexsdm.postech.oficina.cliente.core.usecase.impl;

import br.com.alexsdm.postech.oficina.cliente.core.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.core.domain.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.output.BuscarClientePorDocumentoOutput;
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
class BuscarClientePorDocumentoUsecaseImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private BuscarClientePorDocumentoUsecaseImpl buscarClientePorDocumentoUsecase;

    @Test
    void deveBuscarClientePorDocumentoComSucesso() {
        var documento = "12345678901";
        var cliente = Cliente.builder()
                .id(UUID.randomUUID())
                .nome("Cliente")
                .sobrenome("Teste")
                .cpfCnpj(documento)
                .build();

        var expectedOutput = BuscarClientePorDocumentoOutput.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpfCnpj(cliente.getCpfCnpj())
                .build();

        when(clienteRepository.buscarPorDocumento(documento)).thenReturn(Optional.of(cliente));

        try (MockedStatic<BuscarClientePorDocumentoOutput> mockedStatic = Mockito.mockStatic(BuscarClientePorDocumentoOutput.class)) {
            mockedStatic.when(() -> BuscarClientePorDocumentoOutput.toOutput(cliente)).thenReturn(expectedOutput);

            var output = buscarClientePorDocumentoUsecase.executar(documento);

            assertNotNull(output);
            assertEquals(cliente.getId(), output.id());
            assertEquals(cliente.getNome(), output.nome());
            assertEquals(cliente.getCpfCnpj(), output.cpfCnpj());
        }
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontradoPorDocumento() {
        var documento = "12345678901";
        when(clienteRepository.buscarPorDocumento(documento)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            buscarClientePorDocumentoUsecase.executar(documento);
        });
    }
}
