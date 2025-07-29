package br.com.alexsdm.postech.oficina.veiculos.controller;

import br.com.alexsdm.postech.oficina.veiculos.controller.request.AtualizarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.veiculos.controller.request.CadastrarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.veiculos.service.VeiculoModeloService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/veiculos")
public class VeiculoModeloController {

    private final VeiculoModeloService service;

    public VeiculoModeloController(VeiculoModeloService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody CadastrarVeiculoModeloRequest request) {
        var modelo = service.cadastrar(request);
        return ResponseEntity.ok(modelo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable UUID id) {
        var modelo = service.buscar(id);
        return ResponseEntity.ok(modelo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable UUID id,
                                       @RequestBody AtualizarVeiculoModeloRequest request) {
        var modelo = service.atualizar(id, request);
        return ResponseEntity.ok(modelo);
    }
}
