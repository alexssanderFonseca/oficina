package br.com.alexsdm.postech.oficina.admin.servico.service.application;


import br.com.alexsdm.postech.oficina.admin.servico.controller.request.CadastrarServicoRequest;
import br.com.alexsdm.postech.oficina.admin.servico.controller.request.ServicoAtualizarRequest;
import br.com.alexsdm.postech.oficina.admin.servico.exception.ServicoNaoEncontradoException;
import br.com.alexsdm.postech.oficina.admin.servico.entity.Servico;
import br.com.alexsdm.postech.oficina.admin.servico.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoApplicationService {

    private final ServicoRepository servicoRepository;

    public Long cadastrar(CadastrarServicoRequest cadastrarServicoRequest) {
        var servico = new Servico(
                cadastrarServicoRequest.nome(),
                cadastrarServicoRequest.descricao(),
                cadastrarServicoRequest.preco(),
                cadastrarServicoRequest.duracaoEstimada(),
                cadastrarServicoRequest.categoria(),
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        servicoRepository.save(servico);
        return servico.getId();
    }

    public List<Servico> listar() {
        return servicoRepository.findAll();
    }

    public Servico buscar(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(ServicoNaoEncontradoException::new);
    }

    public Servico atualizar(Long id, ServicoAtualizarRequest request) {
        var servico = servicoRepository.findById(id)
                .orElseThrow(ServicoNaoEncontradoException::new);

        servico.atualizar(request.preco(), request.ativo());

        return servicoRepository.save(servico);
    }

    public void deletar(Long id) {
        if (!servicoRepository.existsById(id)) {
            throw new ServicoNaoEncontradoException();
        }
        servicoRepository.deleteById(id);
    }
}
