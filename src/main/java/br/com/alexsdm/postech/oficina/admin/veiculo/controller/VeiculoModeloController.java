package br.com.alexsdm.postech.oficina.admin.veiculo.controller;

import br.com.alexsdm.postech.oficina.admin.veiculo.controller.request.AtualizarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.admin.veiculo.controller.request.CadastrarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.admin.veiculo.service.VeiculoApplicationModeloService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/veiculos-modelos")
public class VeiculoModeloController {

    private final VeiculoApplicationModeloService service;

    public VeiculoModeloController(VeiculoApplicationModeloService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastrarVeiculoModeloRequest request) {
        var idModelo = service.cadastrar(request);
        return ResponseEntity.created(URI.create("/veiculos-modelos/" + idModelo)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        var modelo = service.buscar(id);
        return ResponseEntity.ok(modelo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @RequestBody @Valid AtualizarVeiculoModeloRequest request) {
        var modelo = service.atualizar(id, request);
        return ResponseEntity.ok(modelo);
    }
}
