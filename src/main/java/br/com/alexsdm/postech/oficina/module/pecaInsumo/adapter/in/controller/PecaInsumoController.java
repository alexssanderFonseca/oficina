package br.com.alexsdm.postech.oficina.module.pecaInsumo.adapter.in.controller;

import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.port.in.*;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.core.usecase.input.ListarPecasInsumoUseCaseInput;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.adapter.in.controller.request.AtualizarPecaInsumoRequest;
import br.com.alexsdm.postech.oficina.module.pecaInsumo.adapter.in.controller.request.CadastrarPecaInsumoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

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
    public ResponseEntity<?> listarTodas(@RequestParam Long pagina,
                                         @RequestParam(required = false, defaultValue = "10") Long quantidade) {
        var output = listarPecasInsumoUseCase.executar(new ListarPecasInsumoUseCaseInput(pagina, quantidade));
        var response = output
                .conteudo()
                .stream()
                .map(mapper::toResponse)
                .toList();
        var data = Map.of("pagina", output.pagina(),
                "totalElementos", output.totalElementos(),
                "quantidade", quantidade,
                "data", response);
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Buscar peça/insumo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable UUID id) {
        var peca = buscarPecaInsumoPorIdUseCase.executar(id);
        return ResponseEntity.ok(mapper.toResponse(peca));
    }

    @Operation(summary = "Cadastrar nova peça ou insumo")
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastrarPecaInsumoRequest request) {
        var dto = mapper.toInput(request);
        var peca = cadastrarPecaInsumoUseCase.executar(dto);
        return ResponseEntity.created(URI.create("/pecas/" + peca.getId())).build();
    }

    @Operation(summary = "Atualizar peça ou insumo")
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable UUID id, @RequestBody @Valid AtualizarPecaInsumoRequest request) {
        var input = mapper.toInput(id, request);
        atualizarPecaInsumoUseCase.executar(input);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deletar peça ou insumo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarPecaInsumoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }
}
