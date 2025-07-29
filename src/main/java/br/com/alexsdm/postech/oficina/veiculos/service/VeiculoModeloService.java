package br.com.alexsdm.postech.oficina.veiculos.service;

import br.com.alexsdm.postech.oficina.veiculos.controller.request.AtualizarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.veiculos.controller.request.CadastrarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.veiculos.exception.VeiculoModeloNaoEncontradoException;
import br.com.alexsdm.postech.oficina.veiculos.model.VeiculoModelo;
import br.com.alexsdm.postech.oficina.veiculos.repository.VeiculoModeloRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VeiculoModeloService {

    private final VeiculoModeloRepository repository;

    public VeiculoModeloService(VeiculoModeloRepository repository) {
        this.repository = repository;
    }

    public VeiculoModelo cadastrar(CadastrarVeiculoModeloRequest request) {
        var modelo = new VeiculoModelo(
                UUID.randomUUID(),
                request.marca(),
                request.modelo(),
                request.anoInicio(),
                request.anoFim(),
                request.tipo()
        );
        return repository.save(modelo);
    }

    public VeiculoModelo buscar(UUID id) {
        return repository.findById(id)
                .orElseThrow(VeiculoModeloNaoEncontradoException::new);
    }

    public VeiculoModelo atualizar(UUID id, AtualizarVeiculoModeloRequest request) {
        var modelo = repository.findById(id)
                .orElseThrow(VeiculoModeloNaoEncontradoException::new);

        modelo = new VeiculoModelo(
                modelo.getId(),
                request.marca(),
                request.modelo(),
                request.anoInicio(),
                request.anoFim(),
                request.tipo()
        );

        return repository.save(modelo);
    }

    public void deletar(UUID id) {
        if (!repository.existsById(id)) {
            throw new VeiculoModeloNaoEncontradoException();
        }
        repository.deleteById(id);
    }
}
