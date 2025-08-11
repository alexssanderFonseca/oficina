package br.com.alexsdm.postech.oficina.admin.veiculo.controller;

import br.com.alexsdm.postech.oficina.admin.veiculo.controller.request.AtualizarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.admin.veiculo.controller.request.CadastrarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.admin.veiculo.service.VeiculoModeloService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/veiculos")
public class VeiculoModeloController {

    private final VeiculoModeloService service;

    public VeiculoModeloController(VeiculoModeloService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody CadastrarVeiculoModeloRequest request) {
        var idModelo = service.cadastrar(request);
        return ResponseEntity.created(URI.create("/veiculos/" + idModelo)).build();
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
                                       @RequestBody AtualizarVeiculoModeloRequest request) {
        var modelo = service.atualizar(id, request);
        return ResponseEntity.ok(modelo);
    }
}
