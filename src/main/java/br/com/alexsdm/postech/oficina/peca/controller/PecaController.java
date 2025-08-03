package br.com.alexsdm.postech.oficina.peca.controller;


import br.com.alexsdm.postech.oficina.peca.controller.input.CadastrarPecaRequest;
import br.com.alexsdm.postech.oficina.peca.model.Peca;
import br.com.alexsdm.postech.oficina.peca.service.PecaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pecas")
public class PecaController {

    private final PecaService pecaService;

    public PecaController(PecaService pecaService) {
        this.pecaService = pecaService;
    }

    @GetMapping
    public List<Peca> listarTodas() {
        return pecaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peca> buscarPorId(@PathVariable Long id) {
        var peca = pecaService.buscarPorId(id);
        return ResponseEntity.ok(peca);
    }

    @PostMapping
    public ResponseEntity<Peca> criar(@RequestBody CadastrarPecaRequest request) {
        Peca salva = pecaService.salvar(request);
        return ResponseEntity.ok(salva);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Peca> atualizar(@PathVariable String id, @RequestBody Peca novaPeca) {
//        return pecaService.buscarPorId(id)
//                .map(p -> {
//                    novaPeca.setId(p.getId());
//                    return ResponseEntity.ok(pecaService.salvar(novaPeca));
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pecaService.deletar(id);
        return ResponseEntity.noContent().build();

    }
}
