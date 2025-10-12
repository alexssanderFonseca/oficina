package br.com.alexsdm.postech.oficina.module.orcamento.core.usecase.input;

import java.util.List;
import java.util.UUID;

public record CriarOrcamentoInput(
        String idCliente,
        String cpfCnpjCliente,
        UUID veiculoId,
        List<CriarOrcamentoItemPecaInput> pecas,
        List<UUID> servicosIds
) {
    public record CriarOrcamentoItemPecaInput(UUID pecaId, Integer quantidade) {
    }
}
