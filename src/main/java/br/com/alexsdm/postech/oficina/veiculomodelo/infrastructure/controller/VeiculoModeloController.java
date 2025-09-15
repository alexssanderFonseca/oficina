package br.com.alexsdm.postech.oficina.veiculomodelo.infrastructure.controller;

import br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase.BuscarVeiculoModeloPorIdUseCase;
import br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase.CadastrarVeiculoModeloUseCase;
import br.com.alexsdm.postech.oficina.veiculomodelo.application.usecase.DeletarVeiculoModeloUseCase;
import br.com.alexsdm.postech.oficina.veiculomodelo.infrastructure.controller.mapper.VeiculoModeloRequestMapper;
import br.com.alexsdm.postech.oficina.veiculomodelo.infrastructure.controller.request.CadastrarVeiculoModeloRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/veiculos-modelos")
@RequiredArgsConstructor
public class VeiculoModeloController {

    private final CadastrarVeiculoModeloUseCase cadastrarVeiculoModeloUseCase;
    private final BuscarVeiculoModeloPorIdUseCase buscarVeiculoModeloPorIdUseCase;
    private final DeletarVeiculoModeloUseCase deletarVeiculoModeloUseCase;
    private final VeiculoModeloRequestMapper mapper;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody CadastrarVeiculoModeloRequest request) {
        var id = cadastrarVeiculoModeloUseCase.executar(mapper.toInput(request));
        return ResponseEntity.created(URI.create("/veiculos-modelos/" + id)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        var modelo = buscarVeiculoModeloPorIdUseCase.executar(id);
        return ResponseEntity.ok(modelo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarVeiculoModeloUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }
}
