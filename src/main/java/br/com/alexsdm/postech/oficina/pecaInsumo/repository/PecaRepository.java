package br.com.alexsdm.postech.oficina.pecaInsumo.repository;

import br.com.alexsdm.postech.oficina.pecaInsumo.entity.PecaInsumo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PecaRepository extends JpaRepository<PecaInsumo, Long> {
}
