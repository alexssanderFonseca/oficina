package br.com.alexsdm.postech.oficina.module.servico.adapter.in.controller;

import br.com.alexsdm.postech.oficina.module.servico.core.port.in.*;
import br.com.alexsdm.postech.oficina.module.servico.adapter.in.controller.mapper.ServicoRequestMapper;
import br.com.alexsdm.postech.oficina.module.servico.adapter.in.controller.request.AtualizarServicoRequest;
import br.com.alexsdm.postech.oficina.module.servico.adapter.in.controller.request.CadastrarServicoRequest;
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
@RequestMapping("/servicos")
@RequiredArgsConstructor
@Tag(name = "Serviços", description = "Gerenciamento dos serviços oferecidos")
public class ServicoController {

    private final CadastrarServicoUseCase cadastrarServicoUseCase;
    private final BuscarServicoPorIdUseCase buscarServicoPorIdUseCase;
    private final ListarServicosUseCase listarServicosUseCase;
    private final DeletarServicoUseCase deletarServicoUseCase;
    private final AtualizarServicoUseCase atualizarServicoUseCase;
    private final ServicoRequestMapper servicoRequestMapper;

    @Operation(summary = "Cadastrar serviço", description = "Cadastra um novo serviço no sistema")
    @PostMapping
    public ResponseEntity<?> cadastrar(
            @Parameter(description = "Dados para cadastro do serviço")
            @RequestBody @Valid CadastrarServicoRequest request) {
        var id = cadastrarServicoUseCase.executar(servicoRequestMapper.toInput(request));
        return ResponseEntity.created(URI.create("/servicos/" + id)).build();
    }

    @Operation(summary = "Buscar serviço por ID", description = "Retorna os detalhes de um serviço específico")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID do serviço", example = "1")
            @PathVariable UUID id) {
        var servico = buscarServicoPorIdUseCase.executar(id);
        return ResponseEntity.ok(servico);
    }

    @Operation(summary = "Listar todos os serviços", description = "Retorna a lista completa de serviços cadastrados")
    @GetMapping
    public ResponseEntity<?> listar() {
        var servicos = listarServicosUseCase.executar();
        return ResponseEntity.ok(servicos);
    }

    @Operation(summary = "Deletar serviço", description = "Remove um serviço pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @Parameter(description = "ID do serviço", example = "1")
            @PathVariable UUID id) {
        deletarServicoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar serviço", description = "Atualiza os dados de um serviço existente")
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @Parameter(description = "ID do serviço", example = "1")
            @PathVariable UUID id,
            @Parameter(description = "Dados para atualização do serviço")
            @RequestBody @Valid AtualizarServicoRequest request) {
        atualizarServicoUseCase.executar(servicoRequestMapper.toInput(id, request));
        return ResponseEntity.ok().build();
    }
}
