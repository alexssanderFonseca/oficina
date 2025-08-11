package br.com.alexsdm.postech.oficina.admin.servico.controller;

import br.com.alexsdm.postech.oficina.admin.servico.controller.request.CadastrarServicoRequest;
import br.com.alexsdm.postech.oficina.admin.servico.controller.request.ServicoAtualizarRequest;
import br.com.alexsdm.postech.oficina.admin.servico.service.application.ServicoApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoApplicationService servicoApplicationService;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastrarServicoRequest request) {
        var servicoID = servicoApplicationService.cadastrar(request);
        return ResponseEntity.created(URI.create("/servicos/" + servicoID)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        var servico = servicoApplicationService.buscar(id);
        return ResponseEntity.ok(servico);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        var servicos = servicoApplicationService.listar();
        return ResponseEntity.ok(servicos);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        servicoApplicationService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @RequestBody @Valid ServicoAtualizarRequest request) {
        var servico = servicoApplicationService.atualizar(id, request);
        return ResponseEntity.ok(servico);
    }
}
