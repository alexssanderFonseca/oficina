package br.com.alexsdm.postech.oficina.module.cliente.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.cliente.core.port.in.DeletarClienteUseCase;
import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.DeletarClienteInput;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.exception.ClienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
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
