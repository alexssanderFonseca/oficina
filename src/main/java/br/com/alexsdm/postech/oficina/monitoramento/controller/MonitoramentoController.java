package br.com.alexsdm.postech.oficina.monitoramento.controller;

import br.com.alexsdm.postech.oficina.monitoramento.application.usecase.CalcularTempoMedioExecucaoUseCase;
import br.com.alexsdm.postech.oficina.monitoramento.controller.response.TempoMedioExecucaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitoramentos")
@RequiredArgsConstructor
public class MonitoramentoController {

    private final CalcularTempoMedioExecucaoUseCase calcularTempoMedioExecucaoUseCase;

    @GetMapping("/execucoes")
    public ResponseEntity<TempoMedioExecucaoResponse> getTempoMedioExecucaoServicos() {
        var tempoMedioEmMinutos = calcularTempoMedioExecucaoUseCase.executar();
        var response = new TempoMedioExecucaoResponse(tempoMedioEmMinutos);
        return ResponseEntity.ok(response);
    }
}
