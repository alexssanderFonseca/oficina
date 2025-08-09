package br.com.alexsdm.postech.oficina.admin.cliente.repository;

import br.com.alexsdm.postech.oficina.admin.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findByCpfCnpj(String s);
}
