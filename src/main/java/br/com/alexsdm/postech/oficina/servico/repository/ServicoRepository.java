package br.com.alexsdm.postech.oficina.servico.repository;

import br.com.alexsdm.postech.oficina.servico.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
