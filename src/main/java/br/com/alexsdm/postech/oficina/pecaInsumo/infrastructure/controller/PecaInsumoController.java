package br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.controller;

import br.com.alexsdm.postech.oficina.pecaInsumo.application.usecase.*;
import br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.controller.request.AtualizarPecaInsumoRequest;
import br.com.alexsdm.postech.oficina.pecaInsumo.infrastructure.controller.request.CadastrarPecaInsumoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pecas")
@RequiredArgsConstructor
@Tag(name = "Peças e Insumos", description = "Gerenciamento de peças e insumos")
public class PecaInsumoController {

    private final CadastrarPecaInsumoUseCase cadastrarPecaInsumoUseCase;
    private final ListarPecasInsumoUseCase listarPecasInsumoUseCase;
    private final BuscarPecaInsumoPorIdUseCase buscarPecaInsumoPorIdUseCase;
    private final AtualizarPecaInsumoUseCase atualizarPecaInsumoUseCase;
    private final DeletarPecaInsumoUseCase deletarPecaInsumoUseCase;
    private final PecaInsumoControllerMapper mapper;

    @Operation(summary = "Listar todas as peças e insumos")
    @GetMapping
    public ResponseEntity<?> listarTodas() {
        var pecas = listarPecasInsumoUseCase.executar().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pecas);
    }

    @Operation(summary = "Buscar peça/insumo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        var peca = buscarPecaInsumoPorIdUseCase.executar(id);
        return ResponseEntity.ok(mapper.toResponse(peca));
    }

    @Operation(summary = "Cadastrar nova peça ou insumo")
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastrarPecaInsumoRequest request) {
        var dto = mapper.toDTO(request);
        var peca = cadastrarPecaInsumoUseCase.executar(dto);
        return ResponseEntity.created(URI.create("/pecas/" + peca.getId())).build();
    }

    @Operation(summary = "Atualizar peça ou insumo")
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizarPecaInsumoRequest request) {
        var dto = mapper.toDTO(id, request);
        var peca = atualizarPecaInsumoUseCase.executar(dto);
        return ResponseEntity.ok(mapper.toResponse(peca));
    }

    @Operation(summary = "Deletar peça ou insumo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarPecaInsumoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }
}
