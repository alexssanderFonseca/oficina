package br.com.alexsdm.postech.oficina.admin.pecaInsumo.repository;

import br.com.alexsdm.postech.oficina.admin.pecaInsumo.model.PecaInsumo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PecaRepository extends JpaRepository<PecaInsumo, Long> {
}
