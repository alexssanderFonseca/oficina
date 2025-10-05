package br.com.alexsdm.postech.oficina.module.ordemServico.adapter.in.controller;

import br.com.alexsdm.postech.oficina.module.ordemServico.core.port.in.*;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.input.CriarOrdemServicoInput;
import br.com.alexsdm.postech.oficina.module.ordemServico.core.usecase.input.FinalizarDiagnosticoInput;
import br.com.alexsdm.postech.oficina.module.ordemServico.adapter.in.controller.request.CriarOrdemDeServicoRequest;
import br.com.alexsdm.postech.oficina.module.ordemServico.adapter.in.controller.request.ExecutarOrdemServicoRequest;
import br.com.alexsdm.postech.oficina.module.ordemServico.adapter.in.controller.request.FinalizarDiagnosticoRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordens-servicos")
@RequiredArgsConstructor
@Tag(name = "Ordens de Serviço", description = "Gerenciamento das ordens de serviço")
public class OrdemServicoController {

    private final AbrirOrdemServicoUseCase abrirOrdemServicoUseCase;
    private final FinalizarDiagnosticoUseCase finalizarDiagnosticoUseCase;
    private final ExecutarOrdemServicoUseCase executarOrdemServicoUseCase;
    private final FinalizarOrdemServicoUseCase finalizarOrdemServicoUseCase;
    private final EntregarOrdemServicoUseCase entregarOrdemServicoUseCase;
    private final BuscarOrdemServicoPorIdUseCase buscarOrdemServicoPorIdUseCase;
    private final ListarOrdensServicoUseCase listarOrdensServicoUseCase;


    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid CriarOrdemDeServicoRequest request) {
        var id = abrirOrdemServicoUseCase.executar(new CriarOrdemServicoInput(request.orcamentoId()));
        return ResponseEntity.created(URI.create("/ordens-servicos/" + id)).build();
    }

    @PostMapping("/{id}/diagnosticos/finalizacoes")
    public ResponseEntity<?> finalizarDiagnostico(@PathVariable UUID id,
                                                  @RequestBody FinalizarDiagnosticoRequest request) {
        var pecasDto = request.idPecasNecessarias()
                .stream()
                .map(peca -> new FinalizarDiagnosticoInput.FinalizarDiagnosticoItemPecaInput(
                        peca.idPeca(),
                        peca.qtd()
                ))
                .collect(Collectors.toList());
        var input = new FinalizarDiagnosticoInput(id, pecasDto, request.idServicosNecessarios());
        var orcamentoId = finalizarDiagnosticoUseCase.executar(input);
        return ResponseEntity.ok(orcamentoId);
    }

    @PostMapping("/{id}/execucoes")
    public ResponseEntity<?> executar(@PathVariable UUID id, @RequestBody ExecutarOrdemServicoRequest request) {
        executarOrdemServicoUseCase.executar(id, request.orcamentoId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/finalizacoes")
    public ResponseEntity<Void> finalizar(@PathVariable UUID id) {
        finalizarOrdemServicoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/entregas")
    public ResponseEntity<?> entregar(@PathVariable UUID id) {
        entregarOrdemServicoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable UUID id) {
        var os = buscarOrdemServicoPorIdUseCase.executar(id);
        return ResponseEntity.ok(os); // TODO: Usar mapper
    }

    @GetMapping
    public ResponseEntity<?> buscar(Pageable pageable) {
        var osPage = listarOrdensServicoUseCase.executar(pageable);
        return ResponseEntity.ok(osPage); // TODO: Usar mapper
    }
}
