package br.com.alexsdm.postech.oficina.admin.veiculo.service;

import br.com.alexsdm.postech.oficina.admin.veiculo.controller.request.AtualizarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.admin.veiculo.controller.request.CadastrarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.admin.veiculo.exception.VeiculoModeloNaoEncontradoException;
import br.com.alexsdm.postech.oficina.admin.veiculo.model.VeiculoModelo;
import br.com.alexsdm.postech.oficina.admin.veiculo.repository.VeiculoModeloRepository;
import org.springframework.stereotype.Service;

@Service
public class VeiculoApplicationModeloService {

    private final VeiculoModeloRepository repository;

    public VeiculoApplicationModeloService(VeiculoModeloRepository repository) {
        this.repository = repository;
    }

    public Long cadastrar(CadastrarVeiculoModeloRequest request) {
        var modelo = new VeiculoModelo(
                request.marca(),
                request.modelo(),
                request.anoInicio(),
                request.anoFim(),
                request.tipo()
        );
        repository.save(modelo);
        return modelo.getId();
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
