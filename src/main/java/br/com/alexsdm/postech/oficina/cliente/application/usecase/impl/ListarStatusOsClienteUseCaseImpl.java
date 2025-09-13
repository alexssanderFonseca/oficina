package br.com.alexsdm.postech.oficina.cliente.application.usecase.impl;

import br.com.alexsdm.postech.oficina.cliente.application.gateway.ClienteGateway;
import br.com.alexsdm.postech.oficina.cliente.application.gateway.OrdemServicoGateway;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.ListarStatusOsClienteUseCase;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.ListarStatusOsClienteInput;
import br.com.alexsdm.postech.oficina.cliente.domain.entity.OrdemServicoStatus;
import br.com.alexsdm.postech.oficina.cliente.exception.ClienteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarStatusOsClienteUseCaseImpl implements ListarStatusOsClienteUseCase {

    private final ClienteGateway clienteGateway;
    private final OrdemServicoGateway ordemServicoGateway;

    @Override
    public List<OrdemServicoStatus> executar(ListarStatusOsClienteInput input) {
        // Garante que o cliente existe antes de prosseguir
        clienteGateway.buscarPorId(input.clienteId())
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com o ID: " + input.clienteId()));

        return ordemServicoGateway.buscarStatusPorCliente(input.clienteId());
    }
}
