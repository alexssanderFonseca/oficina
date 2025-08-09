package br.com.alexsdm.postech.oficina.admin.servico.service.application;


import br.com.alexsdm.postech.oficina.admin.servico.controller.request.CadastrarServicoRequest;
import br.com.alexsdm.postech.oficina.admin.servico.exception.ServicoNaoEncontradoException;
import br.com.alexsdm.postech.oficina.admin.servico.model.Servico;
import br.com.alexsdm.postech.oficina.admin.servico.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoApplicationService {

    private final ServicoRepository servicoRepository;

    public Servico cadastrar(CadastrarServicoRequest cadastrarServicoRequest) {
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
        return servicoRepository.save(servico);
    }

    public List<Servico> listar() {
        return servicoRepository.findAll();
    }

    public Servico buscar(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(ServicoNaoEncontradoException::new);
    }

//    public Servico atualizar(UUID id, ServicoAtualizarRequest request) {
//        var servico = servicoRepository.findById(id)
//                .orElseThrow(ServicoNaoEncontradoException::new);
//
//        servico = new Servico(
//                servico.getId(),
//                request.nome(),
//                request.descricao(),
//                request.preco(),
//                request.duracaoEstimada(),
//                request.categoria(),
//                request.ativo(),
//                servico.getDataCadastro(),
//                LocalDateTime.now()
//        );
//
//        return servicoRepository.save(servico);
//    }

    public void deletar(Long id) {
        if (!servicoRepository.existsById(id)) {
            throw new ServicoNaoEncontradoException();
        }
        servicoRepository.deleteById(id);
    }
}
