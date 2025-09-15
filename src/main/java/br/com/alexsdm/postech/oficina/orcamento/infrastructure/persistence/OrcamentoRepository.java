package br.com.alexsdm.postech.oficina.orcamento.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrcamentoRepository extends JpaRepository<OrcamentoEntity, Long> {
}
