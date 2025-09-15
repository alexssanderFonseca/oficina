package br.com.alexsdm.postech.oficina.monitoramento.domain.entity;

import java.time.LocalDateTime;

public record OrdemServicoConcluida(
    LocalDateTime dataInicioDaExecucao,
    LocalDateTime dataFinalizacao
) {}
