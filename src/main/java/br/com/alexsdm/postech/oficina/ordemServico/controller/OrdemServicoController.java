package br.com.alexsdm.postech.oficina.ordemServico.controller;

import br.com.alexsdm.postech.oficina.ordemServico.controller.request.CriarOrdemDeServicoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.controller.request.ExecutarOrdemServicoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.controller.request.FinalizarDiagnosticoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.model.OrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.service.OrdemServicoService;
import br.com.alexsdm.postech.oficina.ordemServico.service.input.OsPecaNecessariasInput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/ordens-servicos")
@RequiredArgsConstructor
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;


    @PostMapping
    public ResponseEntity<OrdemServico> criar(@RequestBody CriarOrdemDeServicoRequest request) {
        var ordemServicoId = ordemServicoService.criar(request);
        return ResponseEntity.created(URI.create("/ordens-servicos/" + ordemServicoId))
                .build();
    }

    @PostMapping("/{id}/diagnosticos")
    public ResponseEntity<OrdemServico> iniciarDiagnostico(@PathVariable Long id) {
        ordemServicoService.iniciarDiagnostico(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/diagnosticos/finalizacoes")
    public ResponseEntity<?> finalizarDiagnostico(@PathVariable Long id,
                                                  @RequestBody FinalizarDiagnosticoRequest request) {

        var pecasNecessariasOS = request.idPecasNecessarias()
                .stream()
                .map(itemPeca -> new OsPecaNecessariasInput(itemPeca.idPeca(), itemPeca.qtd()))
                .toList();

        ordemServicoService.finalizarDiagnostico(id,
                pecasNecessariasOS,
                request.idServicosNecessarios());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/execucoes")
    public ResponseEntity<?> executar(@PathVariable Long id, @RequestBody ExecutarOrdemServicoRequest
            executarOrdemServicoRequest) {
        ordemServicoService.executar(id, executarOrdemServicoRequest.orcamentoId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/finalizacoes")
    public ResponseEntity<Void> finalizar(@PathVariable Long id) {
        ordemServicoService.finalizar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/entregas")
    public ResponseEntity<?> entregar(@PathVariable Long id) {
        ordemServicoService.entregar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        var ordemServico = ordemServicoService.visualizarOrdemServico(id);
        return ResponseEntity.ok(ordemServico);
    }
}
