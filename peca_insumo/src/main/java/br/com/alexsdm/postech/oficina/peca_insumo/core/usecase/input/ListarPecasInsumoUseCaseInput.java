package br.com.alexsdm.postech.oficina.peca_insumo.core.usecase.input;

public record ListarPecasInsumoUseCaseInput(
        Long pagina,
        Long quantidade
) {
}
