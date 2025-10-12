package br.com.alexsdm.postech.oficina.module.peca_insumo.adapter.out.persistence.jpa;

import br.com.alexsdm.postech.oficina.module.peca_insumo.adapter.out.persistence.entity.PecaInsumoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PecaInsumoJpaRepository extends JpaRepository<PecaInsumoEntity, UUID> {
}
