package br.com.alexsdm.postech.oficina.module.servico.core.port.out;

import br.com.alexsdm.postech.oficina.module.servico.core.domain.entity.Servico;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServicoRepository {
    Servico salvar(Servico servico);
    Optional<Servico> buscarPorId(UUID id);
    List<Servico> listarTodos();
}
