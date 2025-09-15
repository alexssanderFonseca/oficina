package br.com.alexsdm.postech.oficina.orcamento.application.usecase.dto;

import java.util.List;
import java.util.UUID;

public record CriarOrcamentoDTO(
        UUID clienteId,
        UUID veiculoId,
        List<ItemPecaDTO> pecas,
        List<Long> servicosIds
) {
    public record ItemPecaDTO(Long pecaId, Integer quantidade) {}
}
