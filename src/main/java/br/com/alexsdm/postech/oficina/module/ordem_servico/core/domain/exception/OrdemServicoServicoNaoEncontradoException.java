package br.com.alexsdm.postech.oficina.module.ordem_servico.core.domain.exception;

import java.util.UUID;

public class OrdemServicoServicoNaoEncontradoException extends OrdemServicoException {

    public OrdemServicoServicoNaoEncontradoException(UUID id) {
        super("Servico não encontrado para o id solicitado: " + id
        );
    }
}
