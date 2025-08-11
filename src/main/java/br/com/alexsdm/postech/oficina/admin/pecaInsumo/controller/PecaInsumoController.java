package br.com.alexsdm.postech.oficina.admin.pecaInsumo.controller;


import br.com.alexsdm.postech.oficina.admin.pecaInsumo.controller.input.AtualizarPecaInsumoRequest;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.controller.input.CadastrarPecaInsumoRequest;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.service.application.PecaInsumoApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<?> criar(@RequestBody @Valid CadastrarPecaInsumoRequest request) {
        var idPeca = pecaInsumoApplicationService.salvar(request);
        return ResponseEntity.created(URI.create("/pecas/" + idPeca)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id,
                                          @RequestBody @Valid AtualizarPecaInsumoRequest atualizarPecaInsumoRequest) {
        pecaInsumoApplicationService.atualizar(id, atualizarPecaInsumoRequest);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pecaInsumoApplicationService.deletar(id);
        return ResponseEntity.noContent().build();

    }
}
