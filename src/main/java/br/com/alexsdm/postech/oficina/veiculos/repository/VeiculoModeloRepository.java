package br.com.alexsdm.postech.oficina.veiculos.repository;

import br.com.alexsdm.postech.oficina.veiculos.model.VeiculoModelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VeiculoModeloRepository extends JpaRepository<VeiculoModelo, UUID> {}
