package br.com.alexsdm.postech.oficina.orcamento.adapter.out.persistence.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrcamentoJpaRepository extends JpaRepository<OrcamentoEntity, UUID> {
}
