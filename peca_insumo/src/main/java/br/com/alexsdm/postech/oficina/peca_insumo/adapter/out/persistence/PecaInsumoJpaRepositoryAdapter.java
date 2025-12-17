package br.com.alexsdm.postech.oficina.peca_insumo.adapter.out.persistence;

import br.com.alexsdm.postech.oficina.peca_insumo.adapter.out.persistence.jpa.PecaInsumoJpaRepository;
import br.com.alexsdm.postech.oficina.peca_insumo.adapter.out.persistence.mapper.PecaInsumoMapper;
import br.com.alexsdm.postech.oficina.peca_insumo.core.domain.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.peca_insumo.core.domain.pagination.Page;
import br.com.alexsdm.postech.oficina.peca_insumo.core.port.out.PecaInsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PecaInsumoJpaRepositoryAdapter implements PecaInsumoRepository {

    private final PecaInsumoJpaRepository repository;
    private final PecaInsumoMapper mapper;

    @Override
    public PecaInsumo salvar(PecaInsumo pecaInsumo) {
        var entity = mapper.toEntity(pecaInsumo);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<PecaInsumo> buscarPorId(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<PecaInsumo> listarTodos(Long pagina, Long quantidade) {
        var pagePecaInsumo = repository
                .findAll(PageRequest.of(pagina.intValue(), quantidade.intValue()));

        var listaPecasInsumos = pagePecaInsumo.stream()
                .map(mapper::toDomain)
                .toList();

        return new Page<>(listaPecasInsumos,
                pagePecaInsumo.getTotalPages(),
                pagePecaInsumo.getTotalElements(),
                pagePecaInsumo.getNumber());
    }


}
