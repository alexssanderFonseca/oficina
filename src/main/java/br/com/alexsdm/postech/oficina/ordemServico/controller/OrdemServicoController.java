package br.com.alexsdm.postech.oficina.ordemServico.controller;

import br.com.alexsdm.postech.oficina.ordemServico.controller.request.CriarOrdemDeServicoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.controller.request.ExecutarOrdemServicoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.controller.request.FinalizarDiagnosticoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.entity.OrdemServico;
import br.com.alexsdm.postech.oficina.ordemServico.service.application.OrdemServicoApplicationService;
import br.com.alexsdm.postech.oficina.ordemServico.service.application.input.OsPecaNecessariasInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/ordens-servicos")
@RequiredArgsConstructor
@Tag(name = "Ordens de Serviço", description = "Gerenciamento das ordens de serviço")
public class OrdemServicoController {

    private final OrdemServicoApplicationService ordemApplicationService;

    @Operation(summary = "Criar ordem de serviço", description = "Cria uma nova ordem de serviço")
    @PostMapping
    public ResponseEntity<OrdemServico> criar(
            @Parameter(description = "Dados para criação da ordem de serviço")
            @RequestBody @Valid CriarOrdemDeServicoRequest request) {
        var ordemServicoId = ordemApplicationService.criar(request);
        return ResponseEntity.created(URI.create("/ordens-servicos/" + ordemServicoId))
                .build();
    }

    @Operation(summary = "Iniciar diagnóstico da ordem", description = "Inicia o diagnóstico da ordem de serviço")
    @PostMapping("/{id}/diagnosticos")
    public ResponseEntity<OrdemServico> iniciarDiagnostico(
            @Parameter(description = "ID da ordem de serviço", example = "1")
            @PathVariable Long id) {
        ordemApplicationService.iniciarDiagnostico(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Finalizar diagnóstico", description = "Finaliza o diagnóstico informando peças e serviços necessários")
    @PostMapping("/{id}/diagnosticos/finalizacoes")
    public ResponseEntity<?> finalizarDiagnostico(
            @Parameter(description = "ID da ordem de serviço", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Dados para finalizar o diagnóstico")
            @RequestBody FinalizarDiagnosticoRequest request) {

        var pecasNecessariasOS = request.idPecasNecessarias()
                .stream()
                .map(itemPeca -> new OsPecaNecessariasInput(itemPeca.idPeca(), itemPeca.qtd()))
                .toList();

        var orcamentoId = ordemApplicationService.finalizarDiagnostico(id,
                pecasNecessariasOS,
                request.idServicosNecessarios());
        return ResponseEntity.ok(orcamentoId);
    }

    @Operation(summary = "Executar ordem de serviço", description = "Executa a ordem de serviço com base em um orçamento")
    @PostMapping("/{id}/execucoes")
    public ResponseEntity<?> executar(
            @Parameter(description = "ID da ordem de serviço", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Dados para executar a ordem de serviço")
            @RequestBody ExecutarOrdemServicoRequest executarOrdemServicoRequest) {
        ordemApplicationService.executar(id, executarOrdemServicoRequest.orcamentoId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Finalizar ordem de serviço", description = "Finaliza a ordem de serviço")
    @PostMapping("/{id}/finalizacoes")
    public ResponseEntity<Void> finalizar(
            @Parameter(description = "ID da ordem de serviço", example = "1")
            @PathVariable Long id) {
        ordemApplicationService.finalizar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Entregar ordem de serviço", description = "Marca a ordem de serviço como entregue")
    @PostMapping("/{id}/entregas")
    public ResponseEntity<?> entregar(
            @Parameter(description = "ID da ordem de serviço", example = "1")
            @PathVariable Long id) {
        ordemApplicationService.entregar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar ordem de serviço por ID", description = "Retorna os detalhes da ordem de serviço")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(
            @Parameter(description = "ID da ordem de serviço", example = "1")
            @PathVariable Long id) {
        var ordemServico = ordemApplicationService.visualizarOrdemServico(id);
        return ResponseEntity.ok(ordemServico);
    }

    @Operation(summary = "Lista todas as ordens de serviço", description = "Lista todas as ordens")
    @GetMapping()
    public ResponseEntity<?> buscar(Pageable pageable) {
        var ordemServico = ordemApplicationService.listarOrdensServico(pageable);
        return ResponseEntity.ok(ordemServico);
    }


}
