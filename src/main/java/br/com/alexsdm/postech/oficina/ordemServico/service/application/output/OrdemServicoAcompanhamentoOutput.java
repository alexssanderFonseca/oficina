package br.com.alexsdm.postech.oficina.ordemServico.service.application.output;

import java.time.LocalDateTime;

public record OrdemServicoAcompanhamentoOutput(
        Long id,
        LocalDateTime dataHoraCriacao,
        LocalDateTime dataInicioDaExecucao,
        LocalDateTime dataFinalizacao,
        LocalDateTime dataEntrega,
        String status
) {
}
