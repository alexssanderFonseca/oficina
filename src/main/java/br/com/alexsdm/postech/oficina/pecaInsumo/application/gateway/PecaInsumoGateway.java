package br.com.alexsdm.postech.oficina.pecaInsumo.application.gateway;

import br.com.alexsdm.postech.oficina.pecaInsumo.domain.entity.PecaInsumo;
import java.util.List;
import java.util.Optional;

public interface PecaInsumoGateway {
    PecaInsumo salvar(PecaInsumo pecaInsumo);
    Optional<PecaInsumo> buscarPorId(Long id);
    List<PecaInsumo> listarTodos();
}
