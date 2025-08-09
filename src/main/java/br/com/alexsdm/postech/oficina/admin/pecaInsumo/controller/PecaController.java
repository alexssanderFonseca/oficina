package br.com.alexsdm.postech.oficina.admin.pecaInsumo.controller;


import br.com.alexsdm.postech.oficina.admin.pecaInsumo.controller.input.CadastrarPecaRequest;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.model.PecaInsumo;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.service.application.PecaInsumoApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pecas")
@RequiredArgsConstructor
public class PecaController {

    private final PecaInsumoApplicationService pecaApplicationService;


    @GetMapping
    public List<PecaInsumo> listarTodas() {
        return pecaApplicationService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PecaInsumo> buscarPorId(@PathVariable Long id) {
        var peca = pecaApplicationService.buscarPorId(id);
        return ResponseEntity.ok(peca);
    }

    @PostMapping
    public ResponseEntity<PecaInsumo> criar(@RequestBody CadastrarPecaRequest request) {
        PecaInsumo salva = pecaApplicationService.salvar(request);
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
