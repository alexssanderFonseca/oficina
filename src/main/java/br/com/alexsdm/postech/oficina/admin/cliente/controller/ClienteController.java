package br.com.alexsdm.postech.oficina.admin.cliente.controller;


import br.com.alexsdm.postech.oficina.admin.cliente.controller.request.AdicionarDadosVeiculoRequest;
import br.com.alexsdm.postech.oficina.admin.cliente.controller.request.AtualizarClienteRequest;
import br.com.alexsdm.postech.oficina.admin.cliente.controller.request.CadastrarClienteRequest;
import br.com.alexsdm.postech.oficina.admin.cliente.controller.response.AdicionarVeiculoResponse;
import br.com.alexsdm.postech.oficina.admin.cliente.service.application.ClienteApplicationService;
import br.com.alexsdm.postech.oficina.admin.cliente.service.mapper.ClienteServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteApplicationService clienteApplicationService;

    private final ClienteServiceMapper clienteServiceMapper;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody CadastrarClienteRequest request) {
        var cadastrarClienteInput = clienteServiceMapper.toCadastrarClienteInput(request);
        var clienteId = clienteApplicationService.cadastrar(cadastrarClienteInput);
        return ResponseEntity.created(URI.create("/clientes/" + clienteId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable UUID id) {
        var output = clienteApplicationService.buscar(id);
        return ResponseEntity.ok(output);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable UUID id) {
        clienteApplicationService.deletar(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable UUID id,
                                       @RequestBody AtualizarClienteRequest request) {
        var cliente = clienteApplicationService.atualizar(id, request);
        return ResponseEntity.ok(cliente);
    }


    @PostMapping("/{id}/veiculos")
    public ResponseEntity<?> adicionarVeiculo(@PathVariable UUID id,
                                              @RequestBody AdicionarDadosVeiculoRequest request) {
        var adicionarVeiculoInput = clienteServiceMapper.toAdicionarVeiculoClientInput(request);
        var veiculoId = clienteApplicationService.adicionarVeiculo(id, adicionarVeiculoInput);
        return ResponseEntity.ok(new AdicionarVeiculoResponse(veiculoId));
    }
}
