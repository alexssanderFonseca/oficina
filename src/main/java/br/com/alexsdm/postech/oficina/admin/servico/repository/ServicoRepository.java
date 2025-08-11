package br.com.alexsdm.postech.oficina.admin.servico.repository;

import br.com.alexsdm.postech.oficina.admin.servico.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
