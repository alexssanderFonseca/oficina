package br.com.alexsdm.postech.oficina.module.cliente.core.port.out;

import br.com.alexsdm.postech.oficina.module.cliente.core.domain.entity.Cliente;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository {
    Cliente salvar(Cliente cliente);

    Optional<Cliente> buscarPorId(UUID id);

    Optional<Cliente> buscarPorDocumento(String documento);

    void deletar(UUID id);

    boolean isVeiculoJaExistente(String placa);
}