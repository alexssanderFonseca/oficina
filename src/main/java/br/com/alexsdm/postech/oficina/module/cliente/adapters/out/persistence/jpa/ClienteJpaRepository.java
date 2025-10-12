package br.com.alexsdm.postech.oficina.module.cliente.adapters.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ClienteJpaRepository extends JpaRepository<ClienteJpaEntity, UUID> {
    Optional<ClienteJpaEntity> findByCpfCnpj(String cpfCnpj);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM veiculo WHERE placa = :placa)",
            nativeQuery = true)
    boolean existsVeiculoByPlaca(@Param("placa") String placa);
}
