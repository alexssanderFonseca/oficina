package br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.input;

public record ListarPecasInsumoUseCaseInput(
        Long pagina,
        Long quantidade
) {
}
