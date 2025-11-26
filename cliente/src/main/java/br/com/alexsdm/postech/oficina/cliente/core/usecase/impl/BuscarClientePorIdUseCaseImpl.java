package br.com.alexsdm.postech.oficina.cliente.core.usecase.impl;

import br.com.alexsdm.postech.oficina.cliente.core.port.in.BuscarClientePorIdUseCase;
import br.com.alexsdm.postech.oficina.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.output.BuscarClientePorIdOutput;
import br.com.alexsdm.postech.oficina.cliente.core.domain.exception.ClienteNaoEncontradoException;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Named
@RequiredArgsConstructor
public class BuscarClientePorIdUseCaseImpl implements BuscarClientePorIdUseCase {

    private final ClienteRepository clienteRepository;

    @Override
    public BuscarClientePorIdOutput executar(UUID id) {
        return clienteRepository.buscarPorId(id)
                .map(BuscarClientePorIdOutput::toOutput)
                .orElseThrow(ClienteNaoEncontradoException::new);
    }

}