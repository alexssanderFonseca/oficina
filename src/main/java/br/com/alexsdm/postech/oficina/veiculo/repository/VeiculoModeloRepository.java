package br.com.alexsdm.postech.oficina.veiculo.repository;

import br.com.alexsdm.postech.oficina.veiculo.model.VeiculoModelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VeiculoModeloRepository extends JpaRepository<VeiculoModelo, Long> {}
