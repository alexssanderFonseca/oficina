package br.com.alexsdm.postech.oficina.orcamento.controller;

import br.com.alexsdm.postech.oficina.orcamento.controller.request.CriacaoOrcamentoRequest;
import br.com.alexsdm.postech.oficina.orcamento.model.Orcamento;
import br.com.alexsdm.postech.oficina.orcamento.service.application.OrcamentoApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/orcamentos")
@RequiredArgsConstructor
public class OrcamentoController {

    private final OrcamentoApplicationService orcamentoApplicationService;


    @PostMapping
    public ResponseEntity<?> criarOrcamento(@RequestBody @Valid CriacaoOrcamentoRequest input) {
        var orcamentoId = orcamentoApplicationService.criar(
                input.cpfCnpjCliente(),
                input.veiculoId(),
                input.pecas(),
                input.servicos()
        );

        return ResponseEntity
                .created(URI.create("/orcamentos/" + orcamentoId)).build();
    }

    @PostMapping("/{id}/aceitos")
    public ResponseEntity<Orcamento> aceitarOrcamento(@PathVariable Long id) {
        orcamentoApplicationService.aprovar(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/{id}/recusados")
    public ResponseEntity<Orcamento> recusarOrcamento(@PathVariable Long id) {
        orcamentoApplicationService.recusar(id);
        return ResponseEntity.
                noContent().
                build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarOrcamento(@PathVariable Long id) {
        var orcamento = orcamentoApplicationService.buscarPorId(id);
        return ResponseEntity.ok(orcamento);
    }

    @GetMapping("/{id}/envios")
    public ResponseEntity<?> enviarOrcamento(@PathVariable Long id) {
        var pdfBytes = orcamentoApplicationService.enviar(id);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("orcamento.pdf").build());
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}




