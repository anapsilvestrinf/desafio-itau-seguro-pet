package com.itau.localizacao_api.application;

import com.itau.localizacao_api.adapters.GatewayGeocodificacao;
import com.itau.localizacao_api.core.entity.EnderecoCompleto;
import com.itau.localizacao_api.core.entity.PosicaoSensor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocalizacaoCoordenadasServiceTest {
    @Mock
    private GatewayGeocodificacao gateway;

    @InjectMocks
    private LocalizacaoCoordenadasService localizacaoService;

    @Test
    public void deveRetornarEnderecoCompleto_QuandoGatewayRetornaSucesso() throws Exception {
        PosicaoSensor posicaoSensor = this.criaPosicaoSensorMock();
        EnderecoCompleto enderecoEsperado = new EnderecoCompleto(
                "Brasil", "São Paulo", "São Paulo", "", "Rua XYZ"
        );

        when(gateway.buscaEndereco(posicaoSensor.latitude(), posicaoSensor.longitude()))
                .thenReturn(enderecoEsperado);

        EnderecoCompleto resultado = localizacaoService.getPosicao(posicaoSensor);

        assertThat(resultado).isNotNull();
        assertThat(resultado.pais()).isEqualTo(enderecoEsperado.pais());
        assertThat(resultado.estado()).isEqualTo(enderecoEsperado.estado());
        assertThat(resultado.cidade()).isEqualTo(enderecoEsperado.cidade());
        assertThat(resultado.bairro()).isEqualTo(enderecoEsperado.bairro());
        assertThat(resultado.endereco()).isEqualTo(enderecoEsperado.endereco());

        verify(gateway, times(1)).buscaEndereco(posicaoSensor.latitude(), posicaoSensor.longitude());
    }

    @Test
    public void deveLancarExcecao_QuandoGatewayFalhar() throws Exception {

        PosicaoSensor posicaoSensor = this.criaPosicaoSensorMock();
        when(gateway.buscaEndereco(posicaoSensor.latitude(), posicaoSensor.longitude()))
                .thenThrow(new RuntimeException("Falha na comunicação com o gateway"));

        assertThatThrownBy(() -> localizacaoService.getPosicao(posicaoSensor));
    }

    private PosicaoSensor criaPosicaoSensorMock(){
        return new PosicaoSensor(12.0023, 12.1234, null);
    }
}