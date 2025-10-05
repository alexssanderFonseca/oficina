package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.output;

public record BuscarOrdemServicoPecasInsumosOutput(
        String nome,
        String descricao,
        Integer quantidade
) {
}