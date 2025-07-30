package br.com.alexsdm.postech.oficina.clientes.repository;

import br.com.alexsdm.postech.oficina.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findByCpfCnpj(String s);
}
