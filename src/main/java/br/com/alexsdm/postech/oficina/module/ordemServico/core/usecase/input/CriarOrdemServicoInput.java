package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.input;

import java.util.List;
import java.util.UUID;

public record CriarOrdemServicoInput(
        UUID orcamentoId
//        List<CriarOrdemServicoItemInsumoInput> itensInsumos,
//        List<CriarOrdemServicoServicoInput> servicos
        ) {


    public record CriarOrdemServicoItemInsumoInput() {

    }

    public record CriarOrdemServicoServicoInput() {
    }
}
