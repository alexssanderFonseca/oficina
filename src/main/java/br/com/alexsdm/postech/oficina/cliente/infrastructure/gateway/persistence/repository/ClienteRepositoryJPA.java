package br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence.repository;

import br.com.alexsdm.postech.oficina.cliente.infrastructure.gateway.persistence.jpa.ClienteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepositoryJPA extends JpaRepository<ClienteJpaEntity, UUID> {
    Optional<ClienteJpaEntity> findByCpfCnpj(String documento);
}
