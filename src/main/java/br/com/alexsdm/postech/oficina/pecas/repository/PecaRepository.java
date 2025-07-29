package br.com.alexsdm.postech.oficina.pecas.repository;

import br.com.alexsdm.postech.oficina.pecas.model.Peca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PecaRepository extends JpaRepository<Peca, String> {
}
