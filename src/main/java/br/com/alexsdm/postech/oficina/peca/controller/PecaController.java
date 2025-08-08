package br.com.alexsdm.postech.oficina.peca.controller;


import br.com.alexsdm.postech.oficina.peca.controller.input.CadastrarPecaRequest;
import br.com.alexsdm.postech.oficina.peca.model.Peca;
import br.com.alexsdm.postech.oficina.peca.service.application.PecaApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pecas")
@RequiredArgsConstructor
public class PecaController {

    private final PecaApplicationService pecaApplicationService;


    @GetMapping
    public List<Peca> listarTodas() {
        return pecaApplicationService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peca> buscarPorId(@PathVariable Long id) {
        var peca = pecaApplicationService.buscarPorId(id);
        return ResponseEntity.ok(peca);
    }

    @PostMapping
    public ResponseEntity<Peca> criar(@RequestBody CadastrarPecaRequest request) {
        Peca salva = pecaApplicationService.salvar(request);
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
        pecaApplicationService.deletar(id);
        return ResponseEntity.noContent().build();

    }
}
