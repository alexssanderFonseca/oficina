package br.com.alexsdm.postech.oficina.module.peca_insumo.core.domain.exception;

public class PecaInsumoIndisponivelException extends PecaInsumoException {
    public PecaInsumoIndisponivelException() {
        super("Item não disponivel em estoque");
    }
}
