package br.com.alexsdm.postech.oficina.pecaInsumo.controller;

import br.com.alexsdm.postech.oficina.pecaInsumo.controller.input.AtualizarPecaInsumoRequest;
import br.com.alexsdm.postech.oficina.pecaInsumo.controller.input.CadastrarPecaInsumoRequest;
import br.com.alexsdm.postech.oficina.pecaInsumo.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.pecaInsumo.service.application.PecaInsumoApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pecas")
@RequiredArgsConstructor
@Tag(name = "Peças e Insumos", description = "Gerenciamento de peças e insumos")
public class PecaInsumoController {

    private final PecaInsumoApplicationService pecaInsumoApplicationService;

    @Operation(summary = "Listar todas as peças e insumos", description = "Retorna a lista completa de peças e insumos cadastrados")
    @GetMapping
    public List<PecaInsumo> listarTodas() {
        return pecaInsumoApplicationService.listarTodas();
    }

    @Operation(summary = "Buscar peça/insumo por ID", description = "Retorna os detalhes da peça ou insumo pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<PecaInsumo> buscarPorId(
            @Parameter(description = "ID da peça ou insumo", example = "1")
            @PathVariable Long id) {
        var peca = pecaInsumoApplicationService.buscarPorId(id);
        return ResponseEntity.ok(peca);
    }

    @Operation(summary = "Cadastrar nova peça ou insumo", description = "Adiciona uma nova peça ou insumo ao sistema")
    @PostMapping
    public ResponseEntity<?> criar(
            @Parameter(description = "Dados para cadastro da peça ou insumo")
            @RequestBody @Valid CadastrarPecaInsumoRequest request) {
        var idPeca = pecaInsumoApplicationService.salvar(request);
        return ResponseEntity.created(URI.create("/pecas/" + idPeca)).build();
    }

    @Operation(summary = "Atualizar peça ou insumo", description = "Atualiza dados de uma peça ou insumo existente")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @Parameter(description = "ID da peça ou insumo", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Dados para atualização da peça ou insumo")
            @RequestBody @Valid AtualizarPecaInsumoRequest atualizarPecaInsumoRequest) {
        pecaInsumoApplicationService.atualizar(id, atualizarPecaInsumoRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar peça ou insumo", description = "Remove uma peça ou insumo pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da peça ou insumo", example = "1")
            @PathVariable Long id) {
        pecaInsumoApplicationService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
