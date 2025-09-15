package br.com.alexsdm.postech.oficina.cliente.application.gateway;

import br.com.alexsdm.postech.oficina.cliente.domain.entity.OrdemServicoStatus;

import java.util.List;
import java.util.UUID;

public interface ClienteOrdemServicoGateway {
    List<OrdemServicoStatus> buscarStatusPorCliente(UUID clienteId);
}
