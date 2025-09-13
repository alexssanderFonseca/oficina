package br.com.alexsdm.postech.oficina.cliente.application.usecase.impl;

import br.com.alexsdm.postech.oficina.cliente.application.gateway.ClienteGateway;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.BuscarClientePorIdUseCase;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.BuscarClientePorIdInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.BuscarClientePorIdOutput;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteNaoEncontradoException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class BuscarClientePorIdUseCaseImpl implements BuscarClientePorIdUseCase {

    private final ClienteGateway clienteGateway;

    @Override
    public BuscarClientePorIdOutput executar(BuscarClientePorIdInput input) {
        return clienteGateway.buscarPorId(input.id())
                .map(BuscarClientePorIdOutput::toOutput)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com o ID: " + input.id()));
    }

}