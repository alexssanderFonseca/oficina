package br.com.alexsdm.postech.oficina.orcamento.controller;

import br.com.alexsdm.postech.oficina.orcamento.controller.input.CriacaoOrcamentoInput;
import br.com.alexsdm.postech.oficina.orcamento.model.Orcamento;
import br.com.alexsdm.postech.oficina.orcamento.service.OrcamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/orcamentos")
public class OrcamentoController {

    private final OrcamentoService orcamentoService;

    public OrcamentoController(OrcamentoService orcamentoService) {
        this.orcamentoService = orcamentoService;
    }

    @PostMapping
    public ResponseEntity<Orcamento> criarOrcamento(@RequestBody CriacaoOrcamentoInput input) {
        Orcamento orcamento = orcamentoService.criar(
                input.cpfCnpjCliente(),
                input.pecas(),
                input.servicos()
        );

        return ResponseEntity
                .created(URI.create("/orcamentos/" + orcamento.getId()))
                .body(orcamento);
    }

//    @GetMapping("/{id}/orcamentos")
//    public ResponseEntity<List<Orcamento>> listarOrcamentos(@PathVariable Long id) {
//        List<Orcamento> orcamentos = orcamentoService.listarPorOrdemServico(id);
//        return ResponseEntity.ok(orcamentos);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Orcamento> buscarOrcamento(@PathVariable Long id) {
        return orcamentoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping("/{id}/orcamentos/{orcamentoId}/envio")
//    public ResponseEntity<Void> enviarOrcamento(@PathVariable Long id,
//                                                @PathVariable Long orcamentoId) {
//        orcamentoService.enviar(orcamentoId);
//        return ResponseEntity.accepted().build();
//    }
}
