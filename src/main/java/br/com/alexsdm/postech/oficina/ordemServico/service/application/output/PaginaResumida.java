package br.com.alexsdm.postech.oficina.ordemServico.service.application.output;

import java.util.List;

public record PaginaResumida<T>(
        List<T> content,
        long totalElements,
        long page
) {
}
