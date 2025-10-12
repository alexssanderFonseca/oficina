package br.com.alexsdm.postech.oficina.module.monitoramento.core.domain.entity;

import java.time.LocalDateTime;

public record OrdemServicoConcluida(
    LocalDateTime dataInicioDaExecucao,
    LocalDateTime dataFinalizacao
) {}
