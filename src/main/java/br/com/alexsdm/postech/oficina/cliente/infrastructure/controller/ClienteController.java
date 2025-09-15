package br.com.alexsdm.postech.oficina.cliente.infrastructure.controller;

import br.com.alexsdm.postech.oficina.cliente.application.usecase.*;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.BuscarClientePorIdInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.DeletarClienteInput;
import br.com.alexsdm.postech.oficina.cliente.application.usecase.dto.ListarStatusOsClienteInput;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.mapper.ClienteDTOMapper;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.request.AdicionarDadosVeiculoRequest;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.request.AtualizarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.request.CadastrarClienteRequest;
import br.com.alexsdm.postech.oficina.cliente.infrastructure.controller.response.AdicionarVeiculoResponse;
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

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastrarClienteRequest request) {
        var input = clienteDTOMapper.toInput(request);
        var output = cadastrarClienteUseCase.executar(input);
        return ResponseEntity.created(URI.create("/clientes/" + output.id())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable UUID id) {
        var input = new BuscarClientePorIdInput(id);
        var output = buscarClientePorIdUseCase.executar(input);
        return ResponseEntity.ok(output);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable UUID id) {
        var input = new DeletarClienteInput(id);
        deletarClienteUseCase.executar(input);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable UUID id, @RequestBody @Valid AtualizarClienteRequest request) {
        var input = clienteDTOMapper.toAtualizarClienteInput(id, request);
        var output = atualizarClienteUseCase.executar(input);
        return ResponseEntity.ok(output);
    }

    @PostMapping("/{id}/veiculos")
    public ResponseEntity<?> adicionarVeiculo(@PathVariable UUID id, @RequestBody @Valid AdicionarDadosVeiculoRequest request) {
        var input = clienteDTOMapper.toAdicionarVeiculoInput(id, request);
        var output = adicionarVeiculoUseCase.executar(input);
        return ResponseEntity.ok(new AdicionarVeiculoResponse(output.veiculoId()));
    }

    @GetMapping("/{id}/ordensServicos")
    public ResponseEntity<?> statusByCliente(@PathVariable UUID id) {
        var input = new ListarStatusOsClienteInput(id);
        var output = listarStatusOsClienteUseCase.executar(input);
        return ResponseEntity.ok(output);
    }
}
