package br.com.alexsdm.postech.oficina.servico.controller;

import br.com.alexsdm.postech.oficina.servico.controller.request.CadastrarServicoRequest;
import br.com.alexsdm.postech.oficina.servico.controller.request.ServicoAtualizarRequest;
import br.com.alexsdm.postech.oficina.servico.service.application.ServicoApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
@Tag(name = "Serviços", description = "Gerenciamento dos serviços oferecidos")
public class ServicoController {

    private final ServicoApplicationService servicoApplicationService;

    @Operation(summary = "Cadastrar serviço", description = "Cadastra um novo serviço no sistema")
    @PostMapping
    public ResponseEntity<?> cadastrar(
            @Parameter(description = "Dados para cadastro do serviço")
            @RequestBody @Valid CadastrarServicoRequest request) {
        var servicoID = servicoApplicationService.cadastrar(request);
        return ResponseEntity.created(URI.create("/servicos/" + servicoID)).build();
    }

    @Operation(summary = "Buscar serviço por ID", description = "Retorna os detalhes de um serviço específico")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID do serviço", example = "1")
            @PathVariable Long id) {
        var servico = servicoApplicationService.buscar(id);
        return ResponseEntity.ok(servico);
    }

    @Operation(summary = "Listar todos os serviços", description = "Retorna a lista completa de serviços cadastrados")
    @GetMapping
    public ResponseEntity<?> listar() {
        var servicos = servicoApplicationService.listar();
        return ResponseEntity.ok(servicos);
    }

    @Operation(summary = "Deletar serviço", description = "Remove um serviço pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @Parameter(description = "ID do serviço", example = "1")
            @PathVariable Long id) {
        servicoApplicationService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar serviço", description = "Atualiza os dados de um serviço existente")
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @Parameter(description = "ID do serviço", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Dados para atualização do serviço")
            @RequestBody @Valid ServicoAtualizarRequest request) {
        var servico = servicoApplicationService.atualizar(id, request);
        return ResponseEntity.ok(servico);
    }
}
