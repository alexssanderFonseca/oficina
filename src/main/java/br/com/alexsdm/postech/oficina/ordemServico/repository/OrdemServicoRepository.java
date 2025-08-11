package br.com.alexsdm.postech.oficina.ordemServico.repository;

import br.com.alexsdm.postech.oficina.ordemServico.entity.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

    @Query(value = "SELECT AVG(EXTRACT(EPOCH FROM (data_finalizacao - data_inicio_da_execucao))) " +
            "FROM ordem_servico WHERE data_inicio_da_execucao IS NOT NULL AND data_finalizacao IS NOT NULL", nativeQuery = true)
    Double calcularTempoMedioExecucaoSegundos();
}