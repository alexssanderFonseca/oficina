package br.com.alexsdm.postech.oficina.ordemServico.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdemServicoRepository extends JpaRepository<OrdemServicoEntity, Long> {
    List<OrdemServicoEntity> findByStatus(String status);
}
