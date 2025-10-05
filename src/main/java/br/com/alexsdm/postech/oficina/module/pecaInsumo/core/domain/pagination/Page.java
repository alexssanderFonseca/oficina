package br.com.alexsdm.postech.oficina.module.pecaInsumo.core.domain.pagination;

import java.util.List;

public record Page<T>(
        List<T> conteudo,
        int totalElementos,
        int pagina
) {

}
