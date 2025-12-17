package br.com.alexsdm.postech.oficina.cliente.core.port.out;

import br.com.alexsdm.postech.oficina.cliente.core.domain.entity.OrdemServicoStatus;

import java.util.List;
import java.util.UUID;

public interface ClienteOrdemServicoPort {
    List<OrdemServicoStatus> buscarStatusPorCliente(UUID clienteId);
}
