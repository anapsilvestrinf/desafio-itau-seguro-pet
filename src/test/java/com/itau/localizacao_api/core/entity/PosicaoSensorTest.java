package com.itau.localizacao_api.core.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PosicaoSensorTest {

    private static Stream<Arguments> provedorPosicoesInvalidas() {
        return Stream.of(
                Arguments.of(null, 12.0023, null),
                Arguments.of(0.2, null, null),
                Arguments.of(90.2, 26.3748, null),
                Arguments.of(-90.2, 26.3748, null),
                Arguments.of(9.2, -186.3748, null),
                Arguments.of(9.2, 186.3748, null)
        );
    }

    @ParameterizedTest
    @MethodSource("provedorPosicoesInvalidas")
    public void criacaoEntidadeDominio_DeveLancarExcecao_QuandoRecebeDadosInvalidos(
            Double latitude, Double longitude, LocalDateTime datahora) {
        assertThatThrownBy(() -> new PosicaoSensor(
                latitude,
                longitude,
                datahora));
    }

    private static Stream<Arguments> provedorPosicoesValidas() {
        return Stream.of(
                Arguments.of(89.2, 26.3748, null),
                Arguments.of(-89.2, 26.3748, null),
                Arguments.of(9.2, -179.3748, null),
                Arguments.of(9.2, 179.3748, null)
        );
    }

    @ParameterizedTest
    @MethodSource("provedorPosicoesValidas")
    public void criacaoEntidadeDominio_NaoDeveLancarExcecao_QuandoRecebeDadosValidos(
            Double latitude, Double longitude, LocalDateTime datahora) {
        assertDoesNotThrow(() -> new PosicaoSensor(
                latitude,
                longitude,
                datahora));
    }

}