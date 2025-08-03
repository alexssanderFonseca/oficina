package br.com.alexsdm.postech.oficina.servico.controller;

import br.com.alexsdm.postech.oficina.servico.controller.request.CadastrarServicoRequest;
import br.com.alexsdm.postech.oficina.servico.service.ServicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody CadastrarServicoRequest request) {
        var servico = servicoService.cadastrar(request);
        return ResponseEntity.ok(servico);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        var servico = servicoService.buscar(id);
        return ResponseEntity.ok(servico);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        var servicos = servicoService.listar();
        return ResponseEntity.ok(servicos);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<?> atualizar(@PathVariable UUID id,
//                                       @RequestBody ServicoAtualizarRequest request) {
//        var servico = servicoService.atualizar(id, request);
//        return ResponseEntity.ok(servico);
//    }
}
