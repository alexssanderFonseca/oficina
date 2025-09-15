package br.com.alexsdm.postech.oficina.orcamento.infrastructure.controller;

import br.com.alexsdm.postech.oficina.orcamento.application.usecase.*;
import br.com.alexsdm.postech.oficina.orcamento.infrastructure.controller.request.CriacaoOrcamentoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/orcamentos")
@RequiredArgsConstructor
@Tag(name = "Orçamentos", description = "Gerenciamento dos orçamentos")
public class OrcamentoController {

    private final CriarOrcamentoUseCase criarOrcamentoUseCase;
    private final AprovarOrcamentoUseCase aprovarOrcamentoUseCase;
    private final RecusarOrcamentoUseCase recusarOrcamentoUseCase;
    private final BuscarOrcamentoPorIdUseCase buscarOrcamentoPorIdUseCase;
    private final EnviarOrcamentoPdfUseCase enviarOrcamentoPdfUseCase;
    private final OrcamentoControllerMapper mapper;

    @Operation(summary = "Criar novo orçamento", description = "Cria um novo orçamento com peças e serviços")
    @PostMapping
    public ResponseEntity<?> criarOrcamento(
            @Parameter(description = "Dados para criação do orçamento")
            @RequestBody @Valid CriacaoOrcamentoRequest request) {
        var dto = mapper.toDTO(request);
        var orcamento = criarOrcamentoUseCase.executar(dto);
        return ResponseEntity.created(URI.create("/orcamentos/" + orcamento.getId())).build();
    }

    @Operation(summary = "Aceitar orçamento", description = "Marca o orçamento como aceito")
    @PostMapping("/{id}/aceitos")
    public ResponseEntity<Void> aceitarOrcamento(
            @Parameter(description = "ID do orçamento", example = "1")
            @PathVariable Long id) {
        aprovarOrcamentoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Recusar orçamento", description = "Marca o orçamento como recusado")
    @PostMapping("/{id}/recusados")
    public ResponseEntity<Void> recusarOrcamento(
            @Parameter(description = "ID do orçamento", example = "1")
            @PathVariable Long id) {
        recusarOrcamentoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar orçamento por ID", description = "Retorna os detalhes do orçamento")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarOrcamento(
            @Parameter(description = "ID do orçamento", example = "1")
            @PathVariable Long id) {
        var orcamento = buscarOrcamentoPorIdUseCase.executar(id);
        return ResponseEntity.ok(mapper.toResponse(orcamento));
    }

    @Operation(summary = "Enviar orçamento em PDF", description = "Gera e envia o orçamento em formato PDF para download")
    @GetMapping("/{id}/envios")
    public ResponseEntity<?> enviarOrcamento(
            @Parameter(description = "ID do orçamento", example = "1")
            @PathVariable Long id) {
        var pdfBytes = enviarOrcamentoPdfUseCase.executar(id);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("orcamento.pdf").build());
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
