package br.com.alexsdm.postech.oficina.clientes.controller;


import br.com.alexsdm.postech.oficina.clientes.controller.request.ClienteAtualizarRequest;
import br.com.alexsdm.postech.oficina.clientes.controller.request.CadastrarClienteRequest;
import br.com.alexsdm.postech.oficina.clientes.controller.request.DadosVeiculoRequest;
import br.com.alexsdm.postech.oficina.clientes.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;


    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody CadastrarClienteRequest request) {
        var cliente = clienteService.cadastrar(request);
        return ResponseEntity.ok()
                .body(cliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable UUID id) {
        var cliente = clienteService.buscar(id);
        return ResponseEntity.ok()
                .body(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable UUID id) {
        clienteService.deletar(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable UUID id,
                                       @RequestBody ClienteAtualizarRequest request) {
        var cliente = clienteService.atualizar(id, request);
        return ResponseEntity.ok(cliente);
    }


    @PostMapping("/{id}/veiculos")
    public ResponseEntity<?> adicionarVeiculo(@PathVariable UUID id,
                                              @RequestBody DadosVeiculoRequest request) {
        this.clienteService.adicionarVeiculo(id, request);
        return ResponseEntity.ok().build();
    }
}
