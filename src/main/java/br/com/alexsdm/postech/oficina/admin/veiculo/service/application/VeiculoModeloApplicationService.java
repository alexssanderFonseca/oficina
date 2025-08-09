package br.com.alexsdm.postech.oficina.admin.veiculo.service.application;

import br.com.alexsdm.postech.oficina.admin.veiculo.controller.request.AtualizarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.admin.veiculo.controller.request.CadastrarVeiculoModeloRequest;
import br.com.alexsdm.postech.oficina.admin.veiculo.exception.VeiculoModeloNaoEncontradoException;
import br.com.alexsdm.postech.oficina.admin.veiculo.model.VeiculoModelo;
import br.com.alexsdm.postech.oficina.admin.veiculo.repository.VeiculoModeloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VeiculoModeloApplicationService {

    private final VeiculoModeloRepository repository;

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

    public VeiculoModelo buscarEntidade(Long id) {
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
