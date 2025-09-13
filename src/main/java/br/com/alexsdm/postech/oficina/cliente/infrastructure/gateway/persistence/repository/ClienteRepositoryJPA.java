package br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence.repository;

import br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepositoryJPA extends JpaRepository<ClienteEntity, UUID> {
    Optional<ClienteEntity> findByCpfCnpj(String documento);
}
