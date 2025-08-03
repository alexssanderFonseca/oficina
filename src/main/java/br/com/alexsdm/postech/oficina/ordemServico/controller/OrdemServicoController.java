package br.com.alexsdm.postech.oficina.ordemServico.controller;

import br.com.alexsdm.postech.oficina.ordemServico.controller.request.CriarOrdemDeServicoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.controller.request.ExecutarOrdemServicoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.controller.request.FinalizarDiagnosticoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.model.OrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.service.OrdemServicoService;
import br.com.alexsdm.postech.oficina.ordemServico.service.input.OsPecaNecessariasInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/ordens-servicos")
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    @PostMapping
    public ResponseEntity<OrdemServico> criar(@RequestBody CriarOrdemDeServicoRequest request) {
        var ordemServico = ordemServicoService.criar(request);
        return ResponseEntity.created(URI.create("/ordens-servicos/" + ordemServico.getId()))
                .body(ordemServico);
    }

    @PostMapping("/{id}/diagnosticos")
    public ResponseEntity<OrdemServico> iniciarDiagnostico(@PathVariable Long id) {
        var ordemServico = ordemServicoService.iniciarDiagnostico(id);
        return ResponseEntity.ok(ordemServico);
    }

    @PostMapping("/{id}/diagnosticos/finalizacoes")
    public ResponseEntity<?> finalizarDiagnostico(@PathVariable Long id,
                                                  @RequestBody FinalizarDiagnosticoRequest request) {

        var pecasNecessariasOS = request.idPecasNecessarias()
                .stream()
                .map(itemPeca -> new OsPecaNecessariasInput(itemPeca.idPeca(), itemPeca.qtd()))
                .toList();
        var os = ordemServicoService.finalizarDiagnostico(id,
                pecasNecessariasOS,
                request.idServicosNecessarios());
        return ResponseEntity.ok(os);
    }

    @PostMapping("/{id}/execucoes")
    public ResponseEntity<?> executar(@PathVariable Long id, @RequestBody ExecutarOrdemServicoRequest executarOrdemServicoRequest) {
        var os = ordemServicoService.executar(id, executarOrdemServicoRequest.orcamentoId());
        return ResponseEntity.ok(os);
    }

    @PostMapping("/{id}/finalizacoes")
    public ResponseEntity<Void> finalizar(@PathVariable Long id) {
        ordemServicoService.finalizar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/entregas")
    public ResponseEntity<?> entregar(@PathVariable Long id) {
        var ordemServico = ordemServicoService.entregar(id);
        return ResponseEntity.ok(ordemServico);
    }
}
