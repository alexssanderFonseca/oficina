package br.com.alexsdm.postech.oficina.admin.pecaInsumo.controller;


import br.com.alexsdm.postech.oficina.admin.pecaInsumo.controller.input.AtualizarPecaInsumoRequest;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.controller.input.CadastrarPecaInsumoRequest;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.model.PecaInsumo;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.service.application.PecaInsumoApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pecas")
@RequiredArgsConstructor
public class PecaInsumoController {

    private final PecaInsumoApplicationService pecaInsumoApplicationService;


    @GetMapping
    public List<PecaInsumo> listarTodas() {
        return pecaInsumoApplicationService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PecaInsumo> buscarPorId(@PathVariable Long id) {
        var peca = pecaInsumoApplicationService.buscarPorId(id);
        return ResponseEntity.ok(peca);
    }

    @PostMapping
    public ResponseEntity<PecaInsumo> criar(@RequestBody CadastrarPecaInsumoRequest request) {
        PecaInsumo salva = pecaInsumoApplicationService.salvar(request);
        return ResponseEntity.ok(salva);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable String id,
                                          @RequestBody AtualizarPecaInsumoRequest atualizarPecaInsumoRequest) {
        pecaInsumoApplicationService.atualizar();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pecaInsumoApplicationService.deletar(id);
        return ResponseEntity.noContent().build();

    }
}
