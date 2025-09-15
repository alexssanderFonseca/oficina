package br.com.alexsdm.postech.oficina.ordemServico.application.usecase;
import br.com.alexsdm.postech.oficina.ordemServico.domain.entity.OrdemServico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface ListarOrdensServicoUseCase { Page<OrdemServico> executar(Pageable pageable); }