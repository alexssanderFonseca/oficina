package br.com.alexsdm.postech.oficina.cliente.controller;

import br.com.alexsdm.postech.oficina.cliente.controller.request.AdicionarDadosVeiculoRequest;
import br.com.alexsdm.postech.oficina.cliente.controller.request.AtualizarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.controller.request.CadastrarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.controller.response.AdicionarVeiculoResponse;
import br.com.alexsdm.postech.oficina.cliente.service.application.ClienteApplicationService;
import br.com.alexsdm.postech.oficina.cliente.service.mapper.ClienteServiceMapper;
import br.com.alexsdm.postech.oficina.ordemServico.service.application.OrdemServicoApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Operações relacionadas a clientes")
public class ClienteController {

    private final ClienteApplicationService clienteApplicationService;

    private final OrdemServicoApplicationService ordemServicoApplicationService;

    private final ClienteServiceMapper clienteServiceMapper;

    @Operation(summary = "Cadastrar cliente", description = "Cadastra um novo cliente no sistema")
    @PostMapping
    public ResponseEntity<?> cadastrar(
            @Parameter(description = "Dados para cadastro de cliente")
            @RequestBody @Valid CadastrarClienteRequest request) {
        var cadastrarClienteInput = clienteServiceMapper.toCadastrarClienteInput(request);
        var clienteId = clienteApplicationService.cadastrar(cadastrarClienteInput);
        return ResponseEntity.created(URI.create("/clientes/" + clienteId)).build();
    }

    @Operation(summary = "Buscar cliente por ID", description = "Retorna os detalhes de um cliente específico")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID do cliente", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id) {
        var output = clienteApplicationService.buscar(id);
        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Deletar cliente", description = "Remove um cliente pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @Parameter(description = "ID do cliente", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id) {
        clienteApplicationService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @Parameter(description = "ID do cliente", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id,
            @Parameter(description = "Dados para atualização do cliente")
            @RequestBody @Valid AtualizarClienteRequest request) {
        var cliente = clienteApplicationService.atualizar(id, request);
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Adicionar veículo ao cliente", description = "Adiciona um veículo à lista de veículos do cliente")
    @PostMapping("/{id}/veiculos")
    public ResponseEntity<?> adicionarVeiculo(
            @Parameter(description = "ID do cliente", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id,
            @Parameter(description = "Dados do veículo a ser adicionado")
            @RequestBody @Valid AdicionarDadosVeiculoRequest request) {
        var adicionarVeiculoInput = clienteServiceMapper.toAdicionarVeiculoClientInput(request);
        var veiculoId = clienteApplicationService.adicionarVeiculo(id, adicionarVeiculoInput);
        return ResponseEntity.ok(new AdicionarVeiculoResponse(veiculoId));
    }


    @Operation(summary = "Status das OS do cliente", description = "Retorna status da OS do cliente")
    @GetMapping("/{id}/ordensServicos")
    public ResponseEntity<?> statusByCliente(
            @Parameter(description = "ID do cliente", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id) {
        var os = ordemServicoApplicationService.statusByCliente(id);
        var output = clienteApplicationService.listStatusOs(os);
        return ResponseEntity.ok(output);
    }

}
