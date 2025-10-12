package br.com.alexsdm.postech.oficina.module.servico.adapter.out.persistence.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServicoJpaRepository extends JpaRepository<ServicoEntity, UUID> {
}
