package br.com.alexsdm.postech.oficina.admin.servico.service.application;

import br.com.alexsdm.postech.oficina.admin.servico.controller.request.CadastrarServicoRequest;
import br.com.alexsdm.postech.oficina.admin.servico.controller.request.ServicoAtualizarRequest;
import br.com.alexsdm.postech.oficina.admin.servico.exception.ServicoNaoEncontradoException;
import br.com.alexsdm.postech.oficina.admin.servico.model.Servico;
import br.com.alexsdm.postech.oficina.admin.servico.repository.ServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para ServicoApplicationService")
class ServicoApplicationServiceTest {

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private ServicoApplicationService servicoApplicationService;

    private Servico servico;
    private Servico servico2;
    private CadastrarServicoRequest cadastrarServicoRequest;
    private ServicoAtualizarRequest servicoAtualizarRequest;

    private Long servicoId = 1L;
    private Long servicoId2 = 2L;
    private LocalDateTime dataAtual = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        servico = new Servico(
                "Troca de Óleo",
                "Troca de óleo do motor",
                BigDecimal.valueOf(80.00),
                30, // 30 minutos
                "Manutenção",
                true,
                dataAtual.minusDays(10),
                dataAtual
        );
        ReflectionTestUtils.setField(servico, "id", servicoId);

        servico2 = new Servico(
                "Alinhamento",
                "Alinhamento das rodas dianteiras",
                BigDecimal.valueOf(50.00),
                45, // 45 minutos
                "Suspensão",
                true,
                dataAtual.minusDays(5),
                dataAtual
        );
        ReflectionTestUtils.setField(servico2, "id", servicoId2);

        cadastrarServicoRequest = new CadastrarServicoRequest(
                "Troca de Óleo",
                "Troca de óleo do motor",
                BigDecimal.valueOf(80.00),
                30,
                "Manutenção"
        );

        servicoAtualizarRequest = new ServicoAtualizarRequest(
                BigDecimal.valueOf(90.00),
                false
        );
    }

    @Test
    @DisplayName("Deve cadastrar serviço com sucesso")
    void deveCadastrarServicoComSucesso() {
        // Arrange
        when(servicoRepository.save(any(Servico.class))).thenReturn(servico);

        // Act
        var resultado = servicoApplicationService.cadastrar(cadastrarServicoRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals("Troca de Óleo", resultado.getNome());
        assertEquals("Troca de óleo do motor", resultado.getDescricao());
        assertEquals(BigDecimal.valueOf(80.00), resultado.getPreco());
        assertEquals(30, resultado.getDuracaoEstimada());
        assertEquals("Manutenção", resultado.getCategoria());
        assertTrue(resultado.getAtivo());
        verify(servicoRepository).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve cadastrar serviço com valores mínimos")
    void deveCadastrarServicoComValoresMinimos() {
        // Arrange
        var cadastrarServicoMinimo = new CadastrarServicoRequest(
                "Serviço Básico",
                "",
                BigDecimal.ZERO,
                1,
                ""
        );

        var servicoMinimo = new Servico(
                "Serviço Básico",
                "",
                BigDecimal.ZERO,
                1,
                "",
                true,
                dataAtual,
                dataAtual
        );

        when(servicoRepository.save(any(Servico.class))).thenReturn(servicoMinimo);

        // Act
        var resultado = servicoApplicationService.cadastrar(cadastrarServicoMinimo);

        // Assert
        assertNotNull(resultado);
        assertEquals("Serviço Básico", resultado.getNome());
        assertEquals("", resultado.getDescricao());
        assertEquals(BigDecimal.ZERO, resultado.getPreco());
        assertEquals(1, resultado.getDuracaoEstimada());
        assertEquals("", resultado.getCategoria());
        assertTrue(resultado.getAtivo());
        verify(servicoRepository).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve listar todos os serviços com sucesso")
    void deveListarTodosOsServicosComSucesso() {
        // Arrange
        var listaServicos = Arrays.asList(servico, servico2);
        when(servicoRepository.findAll()).thenReturn(listaServicos);

        // Act
        var resultado = servicoApplicationService.listar();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Troca de Óleo", resultado.get(0).getNome());
        assertEquals("Alinhamento", resultado.get(1).getNome());
        verify(servicoRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver serviços")
    void deveRetornarListaVaziaQuandoNaoHouverServicos() {
        // Arrange
        when(servicoRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        var resultado = servicoApplicationService.listar();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(servicoRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar serviço por ID com sucesso")
    void deveBuscarServicoPorIdComSucesso() {
        // Arrange
        when(servicoRepository.findById(servicoId)).thenReturn(Optional.of(servico));

        // Act
        var resultado = servicoApplicationService.buscar(servicoId);

        // Assert
        assertNotNull(resultado);
        assertEquals(servicoId, resultado.getId());
        assertEquals("Troca de Óleo", resultado.getNome());
        assertEquals("Troca de óleo do motor", resultado.getDescricao());
        assertEquals(BigDecimal.valueOf(80.00), resultado.getPreco());
        assertEquals(30, resultado.getDuracaoEstimada());
        assertEquals("Manutenção", resultado.getCategoria());
        assertTrue(resultado.getAtivo());
        verify(servicoRepository).findById(servicoId);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar serviço inexistente")
    void deveLancarExcecaoAoBuscarServicoInexistente() {
        // Arrange
        when(servicoRepository.findById(servicoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ServicoNaoEncontradoException.class, () -> {
            servicoApplicationService.buscar(servicoId);
        });
        verify(servicoRepository).findById(servicoId);
    }

    @Test
    @DisplayName("Deve atualizar serviço com sucesso")
    void deveAtualizarServicoComSucesso() {
        // Arrange
        when(servicoRepository.findById(servicoId)).thenReturn(Optional.of(servico));
        when(servicoRepository.save(any(Servico.class))).thenReturn(servico);

        // Act
        var resultado = servicoApplicationService.atualizar(servicoId, servicoAtualizarRequest);

        // Assert
        assertNotNull(resultado);
        verify(servicoRepository).findById(servicoId);
        verify(servicoRepository).save(servico);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar serviço inexistente")
    void deveLancarExcecaoAoAtualizarServicoInexistente() {
        // Arrange
        when(servicoRepository.findById(servicoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ServicoNaoEncontradoException.class, () -> {
            servicoApplicationService.atualizar(servicoId, servicoAtualizarRequest);
        });
        verify(servicoRepository).findById(servicoId);
        verify(servicoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar serviço apenas com preço")
    void deveAtualizarServicoApenasComPreco() {
        // Arrange
        var atualizarApenasPreco = new ServicoAtualizarRequest(
                BigDecimal.valueOf(120.00),
                true
        );

        when(servicoRepository.findById(servicoId)).thenReturn(Optional.of(servico));
        when(servicoRepository.save(any(Servico.class))).thenReturn(servico);

        // Act
        var resultado = servicoApplicationService.atualizar(servicoId, atualizarApenasPreco);

        // Assert
        assertNotNull(resultado);
        verify(servicoRepository).findById(servicoId);
        verify(servicoRepository).save(servico);
    }

    @Test
    @DisplayName("Deve deletar serviço com sucesso")
    void deveDeletarServicoComSucesso() {
        // Arrange
        when(servicoRepository.existsById(servicoId)).thenReturn(true);

        // Act
        servicoApplicationService.deletar(servicoId);

        // Assert
        verify(servicoRepository).existsById(servicoId);
        verify(servicoRepository).deleteById(servicoId);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar serviço inexistente")
    void deveLancarExcecaoAoDeletarServicoInexistente() {
        // Arrange
        when(servicoRepository.existsById(servicoId)).thenReturn(false);

        // Act & Assert
        assertThrows(ServicoNaoEncontradoException.class, () -> {
            servicoApplicationService.deletar(servicoId);
        });
        verify(servicoRepository).existsById(servicoId);
        verify(servicoRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve cadastrar serviço com duração alta")
    void deveCadastrarServicoComDuracaoAlta() {
        // Arrange
        var cadastrarServicoDuracaoAlta = new CadastrarServicoRequest(
                "Revisão Completa",
                "Revisão completa do veículo",
                BigDecimal.valueOf(500.00),
                480, // 8 horas
                "Revisão"
        );

        var servicoDuracaoAlta = new Servico(
                "Revisão Completa",
                "Revisão completa do veículo",
                BigDecimal.valueOf(500.00),
                480,
                "Revisão",
                true,
                dataAtual,
                dataAtual
        );

        when(servicoRepository.save(any(Servico.class))).thenReturn(servicoDuracaoAlta);

        // Act
        var resultado = servicoApplicationService.cadastrar(cadastrarServicoDuracaoAlta);

        // Assert
        assertNotNull(resultado);
        assertEquals(480, resultado.getDuracaoEstimada());
        assertEquals(BigDecimal.valueOf(500.00), resultado.getPreco());
        verify(servicoRepository).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve cadastrar serviço com preço alto")
    void deveCadastrarServicoComPrecoAlto() {
        // Arrange
        var cadastrarServicoPrecoAlto = new CadastrarServicoRequest(
                "Retífica de Motor",
                "Retífica completa do motor",
                BigDecimal.valueOf(2500.00),
                1440, // 24 horas
                "Motor"
        );

        var servicoPrecoAlto = new Servico(
                "Retífica de Motor",
                "Retífica completa do motor",
                BigDecimal.valueOf(2500.00),
                1440,
                "Motor",
                true,
                dataAtual,
                dataAtual
        );

        when(servicoRepository.save(any(Servico.class))).thenReturn(servicoPrecoAlto);

        // Act
        var resultado = servicoApplicationService.cadastrar(cadastrarServicoPrecoAlto);

        // Assert
        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(2500.00), resultado.getPreco());
        assertEquals("Retífica de Motor", resultado.getNome());
        verify(servicoRepository).save(any(Servico.class));
    }

    @Test
    @DisplayName("Deve atualizar serviço desativando-o")
    void deveAtualizarServicoDesativandoO() {
        // Arrange
        var desativarServico = new ServicoAtualizarRequest(
                BigDecimal.valueOf(80.00),
                false
        );

        when(servicoRepository.findById(servicoId)).thenReturn(Optional.of(servico));
        when(servicoRepository.save(any(Servico.class))).thenReturn(servico);

        // Act
        var resultado = servicoApplicationService.atualizar(servicoId, desativarServico);

        // Assert
        assertNotNull(resultado);
        verify(servicoRepository).findById(servicoId);
        verify(servicoRepository).save(servico);
    }
}