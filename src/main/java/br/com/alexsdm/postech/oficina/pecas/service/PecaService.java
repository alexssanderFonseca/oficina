package br.com.alexsdm.postech.oficina.pecas.service;


import br.com.alexsdm.postech.oficina.pecas.controller.input.CadastrarPecaRequest;
import br.com.alexsdm.postech.oficina.pecas.model.Peca;
import br.com.alexsdm.postech.oficina.pecas.repository.PecaRepository;
import br.com.alexsdm.postech.oficina.veiculos.repository.VeiculoModeloRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PecaService {

    private final PecaRepository pecaRepository;

    private final VeiculoModeloRepository veiculoModeloRepository;

    public PecaService(PecaRepository pecaRepository,
                       VeiculoModeloRepository veiculoModeloRepository) {
        this.pecaRepository = pecaRepository;
        this.veiculoModeloRepository = veiculoModeloRepository;
    }

    public List<Peca> listarTodas() {
        return pecaRepository.findAll();
    }

    public Peca buscarPorId(String id) {
        return pecaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("fafafa"));

    }

    public Peca salvar(CadastrarPecaRequest cadastrarPecaRequest) {
        var modelosCompativeis = cadastrarPecaRequest.idsModelosCompativeis()
                .stream()
                .map(id -> veiculoModeloRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("fafafa")))
                .toList();


        var peca = new Peca(
                UUID.randomUUID(),
                cadastrarPecaRequest.nome(),
                cadastrarPecaRequest.descricao(),
                cadastrarPecaRequest.codigoFabricante(),
                cadastrarPecaRequest.sku(),
                cadastrarPecaRequest.marca(),
                modelosCompativeis,
                cadastrarPecaRequest.quantidadeEstoque(),
                cadastrarPecaRequest.precoCusto(),
                cadastrarPecaRequest.precoVenda(),
                cadastrarPecaRequest.categoria(),
                cadastrarPecaRequest.ativo(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        return pecaRepository.save(peca);
    }

    public void deletar(String id) {
        pecaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("fafafa"));
        pecaRepository.deleteById(id);
    }
}
