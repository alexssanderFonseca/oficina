package br.com.alexsdm.postech.oficina.admin.monitoramento.controller;

import br.com.alexsdm.postech.oficina.admin.monitoramento.service.application.MonitoramentoApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitoramentos")
@RequiredArgsConstructor
public class MonitoramentoController {

    private final MonitoramentoApplicationService monitoramentoApplicationService;

    @GetMapping("/execucoes")
    public ResponseEntity<?> getTempoMedioExecucaoServicos() {
        var tempoMedioExecucoes = monitoramentoApplicationService.obterTempoMedioExecucacaoServicos();
        return ResponseEntity.ok(tempoMedioExecucoes);
    }
}
