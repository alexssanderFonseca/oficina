package br.com.alexsdm.postech.oficina.admin.pecaInsumo.service.application;

import br.com.alexsdm.postech.oficina.admin.pecaInsumo.controller.input.CadastrarPecaInsumoRequest;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.exception.PecaInsumoNaoEncontradaException;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.entity.PecaInsumo;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.repository.PecaRepository;
import br.com.alexsdm.postech.oficina.admin.pecaInsumo.service.domain.PecaInsumoDomainService;
import br.com.alexsdm.postech.oficina.admin.veiculomodelo.model.VeiculoModelo;
import br.com.alexsdm.postech.oficina.admin.veiculomodelo.service.application.VeiculoModeloApplicationService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para PecaInsumoApplicationService")
class PecaInsumoApplicationServiceTest {

    @Mock
    private PecaRepository pecaRepository;

    @Mock
    private VeiculoModeloApplicationService veiculoModeloApplicationService;

    @Mock
    private PecaInsumoDomainService pecaDomainService;

    @InjectMocks
    private PecaInsumoApplicationService pecaInsumoApplicationService;

    private PecaInsumo pecaInsumo;
    private PecaInsumo pecaInsumo2;
    private VeiculoModelo veiculoModelo;
    private VeiculoModelo veiculoModelo2;
    private CadastrarPecaInsumoRequest cadastrarPecaRequest;

    private Long pecaId = 1L;
    private Long pecaId2 = 2L;
    private Long veiculoModeloId = 1L;
    private Long veiculoModeloId2 = 2L;
    private LocalDateTime dataAtual = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        veiculoModelo = new VeiculoModelo("Toyota", "Corolla", 2015, 2025, "Sedan");
        ReflectionTestUtils.setField(veiculoModelo, "id", veiculoModeloId);

        veiculoModelo2 = new VeiculoModelo("Honda", "Civic", 2010, 2020, "Sedan");
        ReflectionTestUtils.setField(veiculoModelo2, "id", veiculoModeloId2);

        pecaInsumo = new PecaInsumo(
                "Pastilha de Freio",
                "Pastilha de freio dianteira",
                "PF001",
                "Bosch",
                Arrays.asList(veiculoModelo),
                10,
                BigDecimal.valueOf(100.00),
                BigDecimal.valueOf(150.00),
                "Freios",
                true,
                dataAtual.minusDays(30),
                dataAtual
        );
        ReflectionTestUtils.setField(pecaInsumo, "id", pecaId);

        pecaInsumo2 = new PecaInsumo(
                "Filtro de Óleo",
                "Filtro de óleo do motor",
                "FO002",
                "Mann",
                Arrays.asList(veiculoModelo2),
                20,
                BigDecimal.valueOf(25.00),
                BigDecimal.valueOf(45.00),
                "Motor",
                true,
                dataAtual.minusDays(15),
                dataAtual
        );
        ReflectionTestUtils.setField(pecaInsumo2, "id", pecaId2);

        cadastrarPecaRequest = new CadastrarPecaInsumoRequest(
                "Pastilha de Freio",
                "Pastilha de freio dianteira",
                "PF001",
                "Bosch",
                Arrays.asList(veiculoModeloId),
                10,
                BigDecimal.valueOf(100.00),
                BigDecimal.valueOf(150.00),
                "Freios",
                true
        );
    }

    @Test
    @DisplayName("Deve listar todas as peças com sucesso")
    void deveListarTodasAsPecasComSucesso() {
        // Arrange
        var listaPecas = Arrays.asList(pecaInsumo, pecaInsumo2);
        when(pecaRepository.findAll()).thenReturn(listaPecas);

        // Act
        var resultado = pecaInsumoApplicationService.listarTodas();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Pastilha de Freio", resultado.get(0).getNome());
        assertEquals("Filtro de Óleo", resultado.get(1).getNome());
        verify(pecaRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver peças")
    void deveRetornarListaVaziaQuandoNaoHouverPecas() {
        // Arrange
        var listaVazia = new ArrayList<PecaInsumo>();
        when(pecaRepository.findAll()).thenReturn(listaVazia);

        // Act
        var resultado = pecaInsumoApplicationService.listarTodas();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(pecaRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar peça por ID com sucesso")
    void deveBuscarPecaPorIdComSucesso() {
        // Arrange
        when(pecaRepository.findById(pecaId)).thenReturn(Optional.of(pecaInsumo));

        // Act
        var resultado = pecaInsumoApplicationService.buscarPorId(pecaId);

        // Assert
        assertNotNull(resultado);
        assertEquals(pecaId, resultado.getId());
        assertEquals("Pastilha de Freio", resultado.getNome());
        assertEquals("PF001", resultado.getCodigoFabricante());
        assertEquals("Bosch", resultado.getMarca());
        assertEquals(BigDecimal.valueOf(100.00), resultado.getPrecoCusto());
        assertEquals(BigDecimal.valueOf(150.00), resultado.getPrecoVenda());
        verify(pecaRepository).findById(pecaId);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar peça inexistente")
    void deveLancarExcecaoAoBuscarPecaInexistente() {
        // Arrange
        when(pecaRepository.findById(pecaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PecaInsumoNaoEncontradaException.class, () -> {
            pecaInsumoApplicationService.buscarPorId(pecaId);
        });
        verify(pecaRepository).findById(pecaId);
    }

    @Test
    @DisplayName("Deve salvar peça com sucesso")
    void deveSalvarPecaComSucesso() {
        // Arrange
        when(veiculoModeloApplicationService.buscarEntidade(veiculoModeloId))
                .thenReturn(veiculoModelo);
        when(pecaRepository.save(any(PecaInsumo.class))).thenReturn(pecaInsumo);

        // Act
        var resultado = pecaInsumoApplicationService.salvar(cadastrarPecaRequest);

        // Assert
        verify(veiculoModeloApplicationService).buscarEntidade(veiculoModeloId);
        verify(pecaRepository).save(any(PecaInsumo.class));
    }

    @Test
    @DisplayName("Deve salvar peça com múltiplos modelos compatíveis")
    void deveSalvarPecaComMultiplosModelosCompativeis() {
        // Arrange
        var cadastrarPecaMultiplosModelos = new CadastrarPecaInsumoRequest(
                "Filtro de Ar",
                "Filtro de ar do motor",
                "FA003",
                "Tecfil",
                Arrays.asList(veiculoModeloId, veiculoModeloId2),
                15,
                BigDecimal.valueOf(30.00),
                BigDecimal.valueOf(50.00),
                "Motor",
                true
        );

        when(veiculoModeloApplicationService.buscarEntidade(veiculoModeloId))
                .thenReturn(veiculoModelo);
        when(veiculoModeloApplicationService.buscarEntidade(veiculoModeloId2))
                .thenReturn(veiculoModelo2);
        when(pecaRepository.save(any(PecaInsumo.class))).thenReturn(pecaInsumo);

        // Act
        var resultado = pecaInsumoApplicationService.salvar(cadastrarPecaMultiplosModelos);

        // Assert
        verify(veiculoModeloApplicationService).buscarEntidade(veiculoModeloId);
        verify(veiculoModeloApplicationService).buscarEntidade(veiculoModeloId2);
        verify(pecaRepository).save(any(PecaInsumo.class));
    }

    @Test
    @DisplayName("Deve salvar peça sem modelos compatíveis")
    void deveSalvarPecaSemModelosCompativeis() {
        // Arrange
        var cadastrarPecaSemModelos = new CadastrarPecaInsumoRequest(
                "Óleo Motor",
                "Óleo lubrificante do motor",
                "OM004",
                "Castrol",
                Arrays.asList(),
                50,
                BigDecimal.valueOf(20.00),
                BigDecimal.valueOf(35.00),
                "Lubrificantes",
                true
        );

        when(pecaRepository.save(any(PecaInsumo.class))).thenReturn(pecaInsumo);

        // Act
        pecaInsumoApplicationService.salvar(cadastrarPecaSemModelos);

        // Assert
        verify(veiculoModeloApplicationService, never()).buscarEntidade(any());
        verify(pecaRepository).save(any(PecaInsumo.class));
    }

    @Test
    @DisplayName("Deve deletar peça com sucesso")
    void deveDeletarPecaComSucesso() {
        // Arrange
        when(pecaRepository.findById(pecaId)).thenReturn(Optional.of(pecaInsumo));

        // Act
        pecaInsumoApplicationService.deletar(pecaId);

        // Assert
        verify(pecaRepository).findById(pecaId);
        verify(pecaRepository).deleteById(pecaId);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar peça inexistente")
    void deveLancarExcecaoAoDeletarPecaInexistente() {
        // Arrange
        when(pecaRepository.findById(pecaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PecaInsumoNaoEncontradaException.class, () -> {
            pecaInsumoApplicationService.deletar(pecaId);
        });
        verify(pecaRepository).findById(pecaId);
        verify(pecaRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve retirar item do estoque com sucesso")
    void deveRetirarItemDoEstoqueComSucesso() {
        // Arrange
        var quantidade = 5;
        when(pecaRepository.findById(pecaId)).thenReturn(Optional.of(pecaInsumo));
        when(pecaRepository.save(any(PecaInsumo.class))).thenReturn(pecaInsumo);

        // Act
        pecaInsumoApplicationService.retirarItemEstoque(pecaId, quantidade);

        // Assert
        verify(pecaRepository).findById(pecaId);
        verify(pecaDomainService).retirarItemEstoque(pecaInsumo, quantidade);
        verify(pecaRepository).save(pecaInsumo);
    }

    @Test
    @DisplayName("Deve lançar exceção ao retirar item de peça inexistente do estoque")
    void deveLancarExcecaoAoRetirarItemDePecaInexistenteDoEstoque() {
        // Arrange
        var quantidade = 5;
        when(pecaRepository.findById(pecaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PecaInsumoNaoEncontradaException.class, () -> {
            pecaInsumoApplicationService.retirarItemEstoque(pecaId, quantidade);
        });
        verify(pecaRepository).findById(pecaId);
        verify(pecaDomainService, never()).retirarItemEstoque(any(), anyInt());
        verify(pecaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve retirar quantidade zero do estoque")
    void deveRetirarQuantidadeZeroDoEstoque() {
        // Arrange
        var quantidade = 0;
        when(pecaRepository.findById(pecaId)).thenReturn(Optional.of(pecaInsumo));
        when(pecaRepository.save(any(PecaInsumo.class))).thenReturn(pecaInsumo);

        // Act
        pecaInsumoApplicationService.retirarItemEstoque(pecaId, quantidade);

        // Assert
        verify(pecaRepository).findById(pecaId);
        verify(pecaDomainService).retirarItemEstoque(pecaInsumo, quantidade);
        verify(pecaRepository).save(pecaInsumo);
    }

    @Test
    @DisplayName("Deve retirar quantidade maior que estoque disponível")
    void deveRetirarQuantidadeMaiorQueEstoqueDisponivel() {
        // Arrange
        var quantidade = 100; // Quantidade maior que o estoque de 10
        when(pecaRepository.findById(pecaId)).thenReturn(Optional.of(pecaInsumo));
        when(pecaRepository.save(any(PecaInsumo.class))).thenReturn(pecaInsumo);

        // Act
        pecaInsumoApplicationService.retirarItemEstoque(pecaId, quantidade);

        // Assert
        verify(pecaRepository).findById(pecaId);
        verify(pecaDomainService).retirarItemEstoque(pecaInsumo, quantidade);
        verify(pecaRepository).save(pecaInsumo);
    }
}