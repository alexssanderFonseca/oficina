package br.com.alexsdm.postech.oficina.servico.adapter.in.controller;

import br.com.alexsdm.postech.oficina.servico.core.port.in.*;
import br.com.alexsdm.postech.oficina.servico.adapter.in.controller.mapper.ServicoRequestMapper;
import br.com.alexsdm.postech.oficina.servico.adapter.in.controller.request.AtualizarServicoRequest;
import br.com.alexsdm.postech.oficina.servico.adapter.in.controller.request.CadastrarServicoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<?> cadastrar(
            @Parameter(description = "Dados para cadastro do serviço")
            @RequestBody @Valid CadastrarServicoRequest request) {
        var id = cadastrarServicoUseCase.executar(servicoRequestMapper.toInput(request));
        return ResponseEntity.created(URI.create("/servicos/" + id)).body(new IdResponse(id));
    }

    @Operation(summary = "Buscar serviço por ID", description = "Retorna os detalhes de um serviço específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço encontrado"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID do serviço")
            @PathVariable UUID id) {
        var servico = buscarServicoPorIdUseCase.executar(id);
        return ResponseEntity.ok(servico);
    }

    @Operation(summary = "Listar todos os serviços", description = "Retorna a lista completa de serviços cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de serviços retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        var servicos = listarServicosUseCase.executar();
        return ResponseEntity.ok(servicos);
    }

    @Operation(summary = "Deletar serviço", description = "Remove um serviço pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Serviço deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @Parameter(description = "ID do serviço")
            @PathVariable UUID id) {
        deletarServicoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar serviço", description = "Atualiza os dados de um serviço existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Serviço não encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @Parameter(description = "ID do serviço")
            @PathVariable UUID id,
            @Parameter(description = "Dados para atualização do serviço")
            @RequestBody @Valid AtualizarServicoRequest request) {
        atualizarServicoUseCase.executar(servicoRequestMapper.toInput(id, request));
        return ResponseEntity.ok().build();
    }
}