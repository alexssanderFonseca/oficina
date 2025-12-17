package br.com.alexsdm.postech.oficina.cliente.core.usecase.impl;

import br.com.alexsdm.postech.oficina.cliente.core.port.in.DeletarClienteUseCase;
import br.com.alexsdm.postech.oficina.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.input.DeletarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.core.domain.exception.ClienteNaoEncontradoException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class DeletarClienteUseCaseImpl implements DeletarClienteUseCase {

    private final ClienteRepository clienteRepository;

    @Override
    public void executar(DeletarClienteInput input) {
        var cliente = clienteRepository.buscarPorId(input.clienteId())
                .orElseThrow(ClienteNaoEncontradoException::new);

        clienteRepository.deletar(cliente.getId());
    }
}
