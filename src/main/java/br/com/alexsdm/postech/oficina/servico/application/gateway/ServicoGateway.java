package br.com.alexsdm.postech.oficina.servico.application.gateway;

import br.com.alexsdm.postech.oficina.servico.domain.entity.Servico;

import java.util.List;
import java.util.Optional;

public interface ServicoGateway {
    Servico salvar(Servico servico);
    Optional<Servico> buscarPorId(Long id);
    List<Servico> listarTodos();
}
