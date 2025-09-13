package br.com.alexsdm.postech.oficina.cliente.application.usecase.impl;

import br.com.alexsdm.postech.oficina.cliente.application.gateway.ClienteGateway;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.DeletarClienteUseCase;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.DeletarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarClienteUseCaseImpl implements DeletarClienteUseCase {

    private final ClienteGateway clienteGateway;

    @Override
    public void executar(DeletarClienteInput input) {
        var cliente = clienteGateway.buscarPorId(input.clienteId())
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com o ID: " + input.clienteId()));
        
        clienteGateway.deletar(cliente.getId());
    }
}
