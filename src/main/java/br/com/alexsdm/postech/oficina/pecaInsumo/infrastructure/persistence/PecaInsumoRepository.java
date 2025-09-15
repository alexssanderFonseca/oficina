package br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PecaInsumoRepository extends JpaRepository<PecaInsumoEntity, Long> {
}
