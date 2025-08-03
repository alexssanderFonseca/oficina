package br.com.alexsdm.postech.oficina.peca.repository;

import br.com.alexsdm.postech.oficina.peca.model.Peca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PecaRepository extends JpaRepository<Peca, Long> {
}
