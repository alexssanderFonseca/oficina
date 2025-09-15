package br.com.alexsdm.postech.oficina.servico.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<ServicoEntity, Long> {
}
