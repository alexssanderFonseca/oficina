package br.com.alexsdm.postech.oficina.veiculomodelo.controller;

import br.com.alexsdm.postech.oficina.veiculomodelo.controller.request.AtualizarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.veiculomodelo.controller.request.CadastrarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.veiculomodelo.service.VeiculoApplicationModeloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/veiculos-modelos")
@Tag(name = "Veículos Modelos", description = "Gerenciamento dos modelos de veículos")
public class VeiculoModeloController {

    private final VeiculoApplicationModeloService service;

    public VeiculoModeloController(VeiculoApplicationModeloService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar modelo de veículo", description = "Adiciona um novo modelo de veículo ao sistema")
    @PostMapping
    public ResponseEntity<?> cadastrar(
            @Parameter(description = "Dados para cadastro do modelo de veículo")
            @RequestBody @Valid CadastrarVeiculoModeloRequest request) {
        var idModelo = service.cadastrar(request);
        return ResponseEntity.created(URI.create("/veiculos-modelos/" + idModelo)).build();
    }

    @Operation(summary = "Buscar modelo de veículo por ID", description = "Retorna os detalhes do modelo de veículo pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID do modelo de veículo", example = "1")
            @PathVariable Long id) {
        var modelo = service.buscar(id);
        return ResponseEntity.ok(modelo);
    }

    @Operation(summary = "Deletar modelo de veículo", description = "Remove um modelo de veículo pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @Parameter(description = "ID do modelo de veículo", example = "1")
            @PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar modelo de veículo", description = "Atualiza os dados de um modelo de veículo existente")
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @Parameter(description = "ID do modelo de veículo", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Dados para atualização do modelo de veículo")
            @RequestBody @Valid AtualizarVeiculoModeloRequest request) {
        var modelo = service.atualizar(id, request);
        return ResponseEntity.ok(modelo);
    }
}
