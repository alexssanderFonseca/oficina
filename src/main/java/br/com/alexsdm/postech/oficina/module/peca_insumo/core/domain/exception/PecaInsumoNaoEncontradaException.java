package br.com.alexsdm.postech.oficina.module.peca_insumo.core.domain.exception;

import java.util.UUID;

public class PecaInsumoNaoEncontradaException extends PecaInsumoException {

    public PecaInsumoNaoEncontradaException(UUID id) {
        super("Peca não encontrada com o id informado : "+ id
        );
    }
}
