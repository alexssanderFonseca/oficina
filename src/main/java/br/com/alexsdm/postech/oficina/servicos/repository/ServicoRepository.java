package br.com.alexsdm.postech.oficina.servicos.repository;

import br.com.alexsdm.postech.oficina.servicos.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServicoRepository extends JpaRepository<Servico, UUID> {
}
