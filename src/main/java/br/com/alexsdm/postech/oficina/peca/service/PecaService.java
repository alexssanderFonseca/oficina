package br.com.alexsdm.postech.oficina.peca.service;


import br.com.alexsdm.postech.oficina.peca.controller.input.CadastrarPecaRequest;
import br.com.alexsdm.postech.oficina.peca.model.Peca;
import br.com.alexsdm.postech.oficina.peca.repository.PecaRepository;
import br.com.alexsdm.postech.oficina.veiculo.repository.VeiculoModeloRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public Peca buscarPorId(Long id) {
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
                cadastrarPecaRequest.nome(),
                cadastrarPecaRequest.descricao(),
                cadastrarPecaRequest.codigoFabricante(),
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

    public void deletar(Long id) {
        pecaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("fafafa"));
        pecaRepository.deleteById(id);
    }

    public void retirarItemEstoque(Peca peca, Integer quantidade) {
        peca.retirar(quantidade);
        pecaRepository.save(peca);

    }
}
