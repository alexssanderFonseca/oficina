package br.com.alexsdm.postech.oficina.module.servico.core.usecase.impl;

import br.com.alexsdm.postech.oficina.module.servico.core.domain.entity.Servico;
import br.com.alexsdm.postech.oficina.module.servico.core.port.out.ServicoRepository;
import br.com.alexsdm.postech.oficina.module.servico.core.usecase.input.CadastrarServicoInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarServicoUseCaseImplTest {

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private CadastrarServicoUseCaseImpl cadastrarServicoUseCase;

    @Test
    void deveCadastrarServicoComSucesso() {
        // Arrange
        var input = new CadastrarServicoInput(
                "Alinhamento",
                "Alinhamento e balanceamento de rodas",
                new BigDecimal("150.00"),
                60,
                "Manutenção Preventiva"
        );

        when(servicoRepository.salvar(any(Servico.class))).thenAnswer(invocation -> {
            Servico servico = invocation.getArgument(0);
            // Simula o comportamento do repositório que retornaria a entidade salva com ID
            return new Servico(
                    servico.getId() != null ? servico.getId() : UUID.randomUUID(),
                    servico.getNome(),
                    servico.getDescricao(),
                    servico.getPreco(),
                    servico.getDuracaoEstimada(),
                    servico.getCategoria()
            );
        });

        // Act
        UUID servicoId = cadastrarServicoUseCase.executar(input);

        // Assert
        assertNotNull(servicoId);
    }
}
