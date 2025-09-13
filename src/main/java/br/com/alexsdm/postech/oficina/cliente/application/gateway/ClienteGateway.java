package br.com.alexsdm.postech.oficina.cliente.application.gateway;

import br.com.alexsdm.postech.oficina.cliente.domain.entity.Cliente;

import java.util.Optional;
import java.util.UUID;

public interface ClienteGateway {
    Cliente salvar(Cliente cliente);

    Optional<Cliente> buscarPorId(UUID id);

    Optional<Cliente> buscarPorDocumento(String documento);

    void deletar(UUID id);
}