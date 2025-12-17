package br.com.alexsdm.postech.oficina.peca_insumo.core.port.out;

import br.com.alexsdm.postech.oficina.peca_insumo.core.domain.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.peca_insumo.core.domain.pagination.Page;

import java.util.Optional;
import java.util.UUID;

public interface PecaInsumoRepository {
    PecaInsumo salvar(PecaInsumo pecaInsumo);

    Optional<PecaInsumo> buscarPorId(UUID id);

    Page<PecaInsumo> listarTodos(Long pagina, Long quantidade);
}
