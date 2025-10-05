package br.com.alexsdm.postech.oficina.module.cliente.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.OrdemServicoStatus;
import br.com.alexsdm.postech.oficina.module.cliente.core.domain.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.module.cliente.core.port.in.ListarStatusOsClienteUseCase;
import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteOrdemServicoPort;
import br.com.alexsdm.postech.oficina.module.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.module.cliente.core.usecase.input.ListarStatusOsClienteInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarStatusOsClienteUseCaseImpl implements ListarStatusOsClienteUseCase {

    private final ClienteRepository clienteRepository;
    private final ClienteOrdemServicoPort ordemServicoGateway;

    @Override
    public List<OrdemServicoStatus> executar(ListarStatusOsClienteInput input) {
        clienteRepository.buscarPorId(input.clienteId())
                .orElseThrow(ClienteNaoEncontradoException::new);

        return ordemServicoGateway.buscarStatusPorCliente(input.clienteId());
    }
}
