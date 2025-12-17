package br.com.alexsdm.postech.oficina.cliente.core.usecase.impl;

import br.com.alexsdm.postech.oficina.cliente.core.domain.entity.Cliente;
import br.com.alexsdm.postech.oficina.cliente.core.domain.entity.Veiculo;
import br.com.alexsdm.postech.oficina.cliente.core.domain.exception.ClienteNaoEncontradoException;
import br.com.alexsdm.postech.oficina.cliente.core.port.out.ClienteRepository;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.input.AdicionarVeiculoInput;
import br.com.alexsdm.postech.oficina.cliente.core.usecase.output.AdicionarVeiculoOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AdicionarVeiculoUseCaseImpl - Testes Unitários")
class AdicionarVeiculoUseCaseImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private AdicionarVeiculoUseCaseImpl adicionarVeiculoUseCase;

    @Captor
    private ArgumentCaptor<Cliente> clienteCaptor;

    private UUID clienteId;
    private Cliente cliente;
    private AdicionarVeiculoInput input;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        cliente = mock(Cliente.class);


        input = new AdicionarVeiculoInput(
                clienteId,

                "Volkswagen",
                "Gol",
                "ABC-1234",
                "2020",
                "Prata"

        );
    }

    @Test
    @DisplayName("Deve adicionar veículo com sucesso quando cliente existe")
    void deveAdicionarVeiculoComSucessoQuandoClienteExiste() {
        // Arrange
        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));

        // Act
        AdicionarVeiculoOutput output = adicionarVeiculoUseCase.executar(input);

        // Assert
        assertThat(output).isNotNull();
        assertThat(output.veiculoId()).isNotNull();

        verify(clienteRepository).buscarPorId(clienteId);
        verify(cliente).adicionarVeiculo(any(Veiculo.class));
        verify(clienteRepository).salvar(cliente);
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não for encontrado")
    void deveLancarExcecaoQuandoClienteNaoForEncontrado() {
        // Arrange
        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> adicionarVeiculoUseCase.executar(input))
                .isInstanceOf(ClienteNaoEncontradoException.class);

        verify(clienteRepository).buscarPorId(clienteId);
        verify(cliente, never()).adicionarVeiculo(any());
        verify(clienteRepository, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve criar veículo com os dados corretos do input")
    void deveCriarVeiculoComDadosCorretosDoInput() {
        // Arrange
        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));
        ArgumentCaptor<Veiculo> veiculoCaptor = ArgumentCaptor.forClass(Veiculo.class);

        // Act
        adicionarVeiculoUseCase.executar(input);

        // Assert
        verify(cliente).adicionarVeiculo(veiculoCaptor.capture());
        Veiculo veiculoCapturado = veiculoCaptor.getValue();

        assertThat(veiculoCapturado).isNotNull();
        assertThat(veiculoCapturado.getId()).isNotNull();
        assertThat(veiculoCapturado.getPlaca()).isEqualTo("ABC-1234");
        assertThat(veiculoCapturado.getMarca()).isEqualTo("Volkswagen");
        assertThat(veiculoCapturado.getModelo()).isEqualTo("Gol");
        assertThat(veiculoCapturado.getCor()).isEqualTo("Prata");
        assertThat(veiculoCapturado.getAno()).isEqualTo("2020");
    }

    @Test
    @DisplayName("Deve gerar UUID único para cada veículo criado")
    void deveGerarUuidUnicoParaCadaVeiculoCriado() {
        // Arrange
        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));
        ArgumentCaptor<Veiculo> veiculoCaptor = ArgumentCaptor.forClass(Veiculo.class);

        // Act
        AdicionarVeiculoOutput output1 = adicionarVeiculoUseCase.executar(input);
        AdicionarVeiculoOutput output2 = adicionarVeiculoUseCase.executar(input);

        // Assert
        assertThat(output1.veiculoId()).isNotEqualTo(output2.veiculoId());

        verify(cliente, times(2)).adicionarVeiculo(veiculoCaptor.capture());
        assertThat(veiculoCaptor.getAllValues().get(0).getId())
                .isNotEqualTo(veiculoCaptor.getAllValues().get(1).getId());
    }

    @Test
    @DisplayName("Deve salvar cliente após adicionar veículo")
    void deveSalvarClienteAposAdicionarVeiculo() {
        // Arrange
        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));

        // Act
        adicionarVeiculoUseCase.executar(input);

        // Assert
        verify(clienteRepository).salvar(clienteCaptor.capture());
        assertThat(clienteCaptor.getValue()).isEqualTo(cliente);
    }

    @Test
    @DisplayName("Deve retornar output com o ID do veículo criado")
    void deveRetornarOutputComIdDoVeiculoCriado() {
        // Arrange
        when(clienteRepository.buscarPorId(clienteId)).thenReturn(Optional.of(cliente));
        ArgumentCaptor<Veiculo> veiculoCaptor = ArgumentCaptor.forClass(Veiculo.class);

        // Act
        AdicionarVeiculoOutput output = adicionarVeiculoUseCase.executar(input);

        // Assert
        verify(cliente).adicionarVeiculo(veiculoCaptor.capture());
        UUID veiculoIdCriado = veiculoCaptor.getValue().getId();

        assertThat(output.veiculoId()).isEqualTo(veiculoIdCriado);
    }
}