package br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.output;

import java.util.List;

public record PaginaResumida<T>(
        List<T> content,
        long totalElements,
        long page
) {
}