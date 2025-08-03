package br.com.alexsdm.postech.oficina.orcamento.repository;

import br.com.alexsdm.postech.oficina.orcamento.model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
}
