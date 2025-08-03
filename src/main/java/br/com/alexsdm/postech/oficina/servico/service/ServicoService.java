package br.com.alexsdm.postech.oficina.servico.service;


import br.com.alexsdm.postech.oficina.servico.controller.request.CadastrarServicoRequest;
import br.com.alexsdm.postech.oficina.servico.model.Servico;
import br.com.alexsdm.postech.oficina.servico.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

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
                .orElseThrow(RuntimeException::new);
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
            throw new RuntimeException("blaba");
        }
        servicoRepository.deleteById(id);
    }
}
