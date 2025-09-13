package br.com.alexsdm.postech.oficina.cliente.infrastructure.controller;

import br.com.alexsdm.postech.oficina.cliente.application.usecase.AdicionarVeiculoUseCase;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.BuscarClientePorIdUseCase;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.CadastrarClienteUseCase;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.DeletarClienteUseCase;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.AtualizarClienteUseCase;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.BuscarClientePorIdInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.DeletarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.request.AdicionarDadosVeiculoRequest;
import br.com.alexsdm.postech.oficina.cliente.controller.request.AtualizarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.controller.response.AdicionarVeiculoResponse;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.mapper.ClienteDTOMapper;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.request.CadastrarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.service.application.ClienteApplicationService;
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
@Tag(name = "Clientes", description = "Operações relacionadas a clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final CadastrarClienteUseCase cadastrarClienteUseCase;
    private final BuscarClientePorIdUseCase buscarClientePorIdUseCase;
    private final AdicionarVeiculoUseCase adicionarVeiculoUseCase;
    private final DeletarClienteUseCase deletarClienteUseCase;
    private final AtualizarClienteUseCase atualizarClienteUseCase;
    private final ClienteDTOMapper clienteDTOMapper;

    // Mantenho os outros services por enquanto para não quebrar os outros endpoints
    private final ClienteApplicationService clienteApplicationService;
    private final OrdemServicoApplicationService ordemServicoApplicationService;

    @Operation(summary = "Cadastrar cliente", description = "Cadastra um novo cliente no sistema")
    @PostMapping
    public ResponseEntity<?> cadastrar(
            @Parameter(description = "Dados para cadastro de cliente")
            @RequestBody @Valid CadastrarClienteRequest request) {
        var input = clienteDTOMapper.toInput(request);
        var output = cadastrarClienteUseCase.executar(input);
        return ResponseEntity.created(URI.create("/clientes/" + output.id())).build();
    }

    @Operation(summary = "Buscar cliente por ID", description = "Retorna os detalhes de um cliente específico")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID do cliente", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id) {
        var input = new BuscarClientePorIdInput(id);
        var output = buscarClientePorIdUseCase.executar(input);
        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Deletar cliente", description = "Remove um cliente pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @Parameter(description = "ID do cliente", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id) {
        var input = new DeletarClienteInput(id);
        deletarClienteUseCase.executar(input);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @Parameter(description = "ID do cliente", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id,
            @Parameter(description = "Dados para atualização do cliente")
            @RequestBody @Valid AtualizarClienteRequest request) {
        var input = clienteDTOMapper.toAtualizarClienteInput(id, request);
        var output = atualizarClienteUseCase.executar(input);
        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Adicionar veículo ao cliente", description = "Adiciona um veículo à lista de veículos do cliente")
    @PostMapping("/{id}/veiculos")
    public ResponseEntity<?> adicionarVeiculo(
            @Parameter(description = "ID do cliente", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id,
            @Parameter(description = "Dados do veículo a ser adicionado")
            @RequestBody @Valid AdicionarDadosVeiculoRequest request) {
        var input = clienteDTOMapper.toAdicionarVeiculoInput(id, request);
        var output = adicionarVeiculoUseCase.executar(input);
        return ResponseEntity.ok(new AdicionarVeiculoResponse(output.veiculoId()));
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