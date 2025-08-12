package br.com.alexsdm.postech.oficina.pecaInsumo.service.application;

import br.com.alexsdm.postech.oficina.pecaInsumo.controller.input.AtualizarPecaInsumoRequest;
import br.com.alexsdm.postech.oficina.pecaInsumo.controller.input.CadastrarPecaInsumoRequest;
import br.com.alexsdm.postech.oficina.pecaInsumo.exception.PecaInsumoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.pecaInsumo.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.pecaInsumo.repository.PecaRepository;
import br.com.alexsdm.postech.oficina.pecaInsumo.service.domain.PecaInsumoDomainService;
import br.com.alexsdm.postech.oficina.veiculomodelo.service.application.VeiculoModeloApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PecaInsumoApplicationService {

    private final PecaRepository pecaRepository;
    private final VeiculoModeloApplicationService veiculoModeloApplicationService;
    private final PecaInsumoDomainService pecaDomainService;


    public List<PecaInsumo> listarTodas() {
        return pecaRepository.findAll();
    }

    public PecaInsumo buscarPorId(Long id) {
        return pecaRepository.findById(id)
                .orElseThrow(PecaInsumoNaoEncontradaException::new);

    }

    public Long salvar(CadastrarPecaInsumoRequest cadastrarPecaRequest) {
        var modelosCompativeis = cadastrarPecaRequest.idsModelosCompativeis()
                .stream()
                .map(veiculoModeloApplicationService::buscarEntidade)
                .toList();


        var peca = new PecaInsumo(
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
        pecaRepository.save(peca);

        return peca.getId();
    }

    public void atualizar(Long pecaId, AtualizarPecaInsumoRequest pecaInsumoRequest) {
        var peca = buscarEntidade(pecaId);
        peca.atualizarPrecos(pecaInsumoRequest.precoCusto(), pecaInsumoRequest.precoVenda());
        peca.atualizarStatus(pecaInsumoRequest.ativa());
        peca.atualizarQuantidadeEstoque(pecaInsumoRequest.qtd());
        pecaRepository.save(peca);
    }

    public void deletar(Long id) {
        buscarEntidade(id);
        pecaRepository.deleteById(id);
    }

    public void retirarItemEstoque(Long pecaId, Integer quantidade) {
        var peca = pecaRepository.findById(pecaId).orElseThrow(PecaInsumoNaoEncontradaException::new);
        pecaDomainService.retirarItemEstoque(peca, quantidade);
        pecaRepository.save(peca);
    }

    private PecaInsumo buscarEntidade(Long id) {
        return pecaRepository.findById(id)
                .orElseThrow(PecaInsumoNaoEncontradaException::new);
    }
}
