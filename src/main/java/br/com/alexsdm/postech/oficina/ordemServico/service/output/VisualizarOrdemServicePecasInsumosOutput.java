package br.com.alexsdm.postech.oficina.ordemServico.service.output;

public record VisualizarOrdemServicePecasInsumosOutput(
        String nome,
        String descricao,
        Integer qtd
) {
}
