package br.com.alexsdm.postech.oficina.module.peca_insumo.core.domain.pagination;

import java.util.List;

public record Page<T>(
        List<T> conteudo,
        long totalPaginas,
        long totalElementos,
        int pagina
) {

}
