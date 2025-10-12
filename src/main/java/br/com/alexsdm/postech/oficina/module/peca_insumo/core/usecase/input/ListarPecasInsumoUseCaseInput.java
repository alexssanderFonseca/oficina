package br.com.alexsdm.postech.oficina.module.peca_insumo.core.usecase.input;

public record ListarPecasInsumoUseCaseInput(
        Long pagina,
        Long quantidade
) {
}
