package br.com.alexsdm.postech.oficina.ordemServico.service.application.output;

public record VisualizarOrdemServicePecasInsumosOutput(
        String nome,
        String descricao,
        Integer qtd
) {
}
