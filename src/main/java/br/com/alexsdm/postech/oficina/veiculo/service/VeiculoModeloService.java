package br.com.alexsdm.postech.oficina.veiculo.service;

import br.com.alexsdm.postech.oficina.veiculo.controller.request.AtualizarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.veiculo.controller.request.CadastrarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.veiculo.exception.VeiculoModeloNaoEncontradoException;
import br.com.alexsdm.postech.oficina.veiculo.model.VeiculoModelo;
import br.com.alexsdm.postech.oficina.veiculo.repository.VeiculoModeloRepository;
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
                request.marca(),
                request.modelo(),
                request.anoInicio(),
                request.anoFim(),
                request.tipo()
        );
        return repository.save(modelo);
    }

    public VeiculoModelo buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(VeiculoModeloNaoEncontradoException::new);
    }

    public VeiculoModelo atualizar(Long id, AtualizarVeiculoModeloRequest request) {
        var modelo = repository.findById(id)
                .orElseThrow(VeiculoModeloNaoEncontradoException::new);

        modelo = new VeiculoModelo(
                request.marca(),
                request.modelo(),
                request.anoInicio(),
                request.anoFim(),
                request.tipo()
        );

        return repository.save(modelo);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new VeiculoModeloNaoEncontradoException();
        }
        repository.deleteById(id);
    }
}
