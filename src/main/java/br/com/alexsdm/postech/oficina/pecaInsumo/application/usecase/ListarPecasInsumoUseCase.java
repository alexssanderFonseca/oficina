package br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase;

import br.com.alexsdm.postech.oficina.pecaInsumo.domain.entity.PecaInsumo;
import java.util.List;

public interface ListarPecasInsumoUseCase {
    List<PecaInsumo> executar();
}
