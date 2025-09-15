package br.com.alexsdm.postech.oficina.ordemServico.infrastructure.controller;

import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.*;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.dto.CriarOrdemServicoDTO;
import br.com.alexsdm.postech.oficina.ordemServico.application.usecase.dto.FinalizarDiagnosticoDTO;
import br.com.alexsdm.postech.oficina.ordemServico.infrastructure.controller.request.CriarOrdemDeServicoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.infrastructure.controller.request.ExecutarOrdemServicoRequest;
import br.com.alexsdm.postech.oficina.ordemServico.infrastructure.controller.request.FinalizarDiagnosticoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ordens-servicos")
@RequiredArgsConstructor
@Tag(name = "Ordens de Serviço", description = "Gerenciamento das ordens de serviço")
public class OrdemServicoController {

    private final CriarOrdemServicoUseCase criarOrdemServicoUseCase;
    private final IniciarDiagnosticoUseCase iniciarDiagnosticoUseCase;
    private final FinalizarDiagnosticoUseCase finalizarDiagnosticoUseCase;
    private final ExecutarOrdemServicoUseCase executarOrdemServicoUseCase;
    private final FinalizarOrdemServicoUseCase finalizarOrdemServicoUseCase;
    private final EntregarOrdemServicoUseCase entregarOrdemServicoUseCase;
    private final BuscarOrdemServicoPorIdUseCase buscarOrdemServicoPorIdUseCase;
    private final ListarOrdensServicoUseCase listarOrdensServicoUseCase;
    // private final OrdemServicoControllerMapper mapper;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid CriarOrdemDeServicoRequest request) {
        var os = criarOrdemServicoUseCase.executar(new CriarOrdemServicoDTO(request.orcamentoId()));
        return ResponseEntity.created(URI.create("/ordens-servicos/" + os.getId())).build();
    }

    @PostMapping("/{id}/diagnosticos")
    public ResponseEntity<Void> iniciarDiagnostico(@PathVariable Long id) {
        iniciarDiagnosticoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/diagnosticos/finalizacoes")
    public ResponseEntity<?> finalizarDiagnostico(@PathVariable Long id, @RequestBody FinalizarDiagnosticoRequest request) {
        var pecasDto = request.idPecasNecessarias().stream()
            .map(p -> new FinalizarDiagnosticoDTO.ItemPecaDTO(p.idPeca(), p.qtd()))
            .collect(Collectors.toList());
        var dto = new FinalizarDiagnosticoDTO(id, pecasDto, request.idServicosNecessarios());
        var orcamentoId = finalizarDiagnosticoUseCase.executar(dto);
        return ResponseEntity.ok(orcamentoId);
    }

    @PostMapping("/{id}/execucoes")
    public ResponseEntity<?> executar(@PathVariable Long id, @RequestBody ExecutarOrdemServicoRequest request) {
        executarOrdemServicoUseCase.executar(id, request.orcamentoId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/finalizacoes")
    public ResponseEntity<Void> finalizar(@PathVariable Long id) {
        finalizarOrdemServicoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/entregas")
    public ResponseEntity<?> entregar(@PathVariable Long id) {
        entregarOrdemServicoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        var os = buscarOrdemServicoPorIdUseCase.executar(id);
        return ResponseEntity.ok(os); // TODO: Usar mapper
    }

    @GetMapping
    public ResponseEntity<?> buscar(Pageable pageable) {
        var osPage = listarOrdensServicoUseCase.executar(pageable);
        return ResponseEntity.ok(osPage); // TODO: Usar mapper
    }
}
