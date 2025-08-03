package br.com.alexsdm.postech.oficina.ordemServico.repository;

import br.com.alexsdm.postech.oficina.ordemServico.model.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
}
