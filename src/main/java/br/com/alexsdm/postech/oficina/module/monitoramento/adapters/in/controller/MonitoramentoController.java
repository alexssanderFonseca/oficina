package br.com.alexsdm.postech.oficina.module.monitoramento.adapters.in.controller;

import br.com.alexsdm.postech.oficina.module.monitoramento.core.port.in.CalcularTempoMedioExecucaoUseCase;
import br.com.alexsdm.postech.oficina.module.monitoramento.adapters.in.controller.response.TempoMedioExecucaoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitoramentos")
@Tag(name = "Monitoramento", description = "Operações relacionadas a métricas e monitoramento da oficina")
@RequiredArgsConstructor
public class MonitoramentoController {

    private final CalcularTempoMedioExecucaoUseCase calcularTempoMedioExecucaoUseCase;

    @Operation(summary = "Calcula o tempo médio de execução de todos os serviços finalizados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cálculo retornado com sucesso")
    })
    @GetMapping("/execucoes")
    public ResponseEntity<TempoMedioExecucaoResponse> getTempoMedioExecucaoServicos() {
        var tempoMedioEmMinutos = calcularTempoMedioExecucaoUseCase.executar();
        var response = new TempoMedioExecucaoResponse(tempoMedioEmMinutos);
        return ResponseEntity.ok(response);
    }
}