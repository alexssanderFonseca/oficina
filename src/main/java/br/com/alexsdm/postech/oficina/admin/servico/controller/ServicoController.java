package br.com.alexsdm.postech.oficina.admin.servico.controller;

import br.com.alexsdm.postech.oficina.admin.servico.controller.request.CadastrarServicoRequest;
import br.com.alexsdm.postech.oficina.admin.servico.service.application.ServicoApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoApplicationService servicoApplicationService;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody CadastrarServicoRequest request) {
        var servico = servicoApplicationService.cadastrar(request);
        return ResponseEntity.ok(servico);
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

//    @PatchMapping("/{id}")
//    public ResponseEntity<?> atualizar(@PathVariable UUID id,
//                                       @RequestBody ServicoAtualizarRequest request) {
//        var servico = servicoService.atualizar(id, request);
//        return ResponseEntity.ok(servico);
//    }
}
