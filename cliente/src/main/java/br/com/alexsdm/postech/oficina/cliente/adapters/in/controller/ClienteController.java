package br.com.alexsdm.postech.oficina.cliente.adapters.in.controller;

import br.com.alexsdm.postech.oficina.cliente.core.port.in.*;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.input.DeletarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.input.ListarStatusOsClienteInput;
import br.com.alexsdm.postech.oficina.cliente.adapters.in.controller.mapper.ClienteDTOMapper;
import br.com.alexsdm.postech.oficina.cliente.adapters.in.controller.request.AdicionarDadosVeiculoRequest;
import br.com.alexsdm.postech.oficina.cliente.adapters.in.controller.request.AtualizarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.adapters.in.controller.request.CadastrarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.adapters.in.controller.response.AdicionarVeiculoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private final ListarStatusOsClienteUseCase listarStatusOsClienteUseCase;
    private final ClienteDTOMapper clienteDTOMapper;

    @Operation(summary = "Cadastra um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastrarClienteRequest request) {
        var input = clienteDTOMapper.toInput(request);
        var output = cadastrarClienteUseCase.executar(input);
        return ResponseEntity.created(URI.create("/clientes/" + output.id())).build();
    }

    @Operation(summary = "Busca um cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable UUID id) {
        var output = buscarClientePorIdUseCase.executar(id);
        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Deleta um cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable UUID id) {
        var input = new DeletarClienteInput(id);
        deletarClienteUseCase.executar(input);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza os dados de um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable UUID id, @RequestBody @Valid AtualizarClienteRequest request) {
        var input = clienteDTOMapper.toAtualizarClienteInput(id, request);
        var output = atualizarClienteUseCase.executar(input);
        return ResponseEntity.ok(output);
    }

    @Operation(summary = "Adiciona um veículo a um cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veículo adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PostMapping("/{id}/veiculos")
    public ResponseEntity<?> adicionarVeiculo(@PathVariable UUID id, @RequestBody @Valid AdicionarDadosVeiculoRequest request) {
        var input = clienteDTOMapper.toAdicionarVeiculoInput(id, request);
        var output = adicionarVeiculoUseCase.executar(input);
        return ResponseEntity.ok(new AdicionarVeiculoResponse(output.veiculoId()));
    }

    @Operation(summary = "Lista o status de todas as ordens de serviço de um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status das ordens de serviço retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}/ordensServicos")
    public ResponseEntity<?> statusByCliente(@PathVariable UUID id) {
        var input = new ListarStatusOsClienteInput(id);
        var output = listarStatusOsClienteUseCase.executar(input);
        return ResponseEntity.ok(output);
    }
}