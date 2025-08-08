package br.com.alexsdm.postech.oficina.peca.service.application;

import br.com.alexsdm.postech.oficina.peca.controller.input.CadastrarPecaRequest;
import br.com.alexsdm.postech.oficina.peca.exception.PecaNaoEncontradaException;
import br.com.alexsdm.postech.oficina.peca.model.Peca;
import br.com.alexsdm.postech.oficina.peca.repository.PecaRepository;
import br.com.alexsdm.postech.oficina.peca.service.domain.PecaDomainService;
import br.com.alexsdm.postech.oficina.veiculo.service.application.VeiculoModeloApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PecaApplicationService {

    private final PecaRepository pecaRepository;
    private final VeiculoModeloApplicationService veiculoModeloApplicationService;
    private final PecaDomainService pecaDomainService;


    public List<Peca> listarTodas() {
        return pecaRepository.findAll();
    }

    public Peca buscarPorId(Long id) {
        return pecaRepository.findById(id)
                .orElseThrow(PecaNaoEncontradaException::new);

    }

    public Peca salvar(CadastrarPecaRequest cadastrarPecaRequest) {
        var modelosCompativeis = cadastrarPecaRequest.idsModelosCompativeis()
                .stream()
                .map(veiculoModeloApplicationService::buscarEntidade)
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
                .orElseThrow(PecaNaoEncontradaException::new);
        pecaRepository.deleteById(id);
    }

    public void retirarItemEstoque(Long pecaId, Integer quantidade) {
        var peca = pecaRepository.findById(pecaId).orElseThrow(PecaNaoEncontradaException::new);
        pecaDomainService.retirarItemEstoque(peca, quantidade);
        pecaRepository.save(peca);
    }
}
