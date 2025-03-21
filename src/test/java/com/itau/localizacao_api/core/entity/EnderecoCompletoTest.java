package com.itau.localizacao_api.core.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EnderecoCompletoTest {

    private static Stream<Arguments> provedorEnderecosInvalidos() {
        return Stream.of(
                Arguments.of(null, "São Paulo", "São Paulo", "Centro", "Rua XYZ"),
                Arguments.of("Brasil", null, "São Paulo", "Centro", "Rua XYZ"),
                Arguments.of("Brasil", "São Paulo", null, "Centro", "Rua XYZ"),
                Arguments.of("", "São Paulo", "São Paulo", "Centro", "Rua XYZ"),
                Arguments.of("Brasil", "", "São Paulo", "Centro", "Rua XYZ"),
                Arguments.of("Brasil", "São Paulo", "", "Centro", "Rua XYZ")
        );
    }

    @ParameterizedTest
    @MethodSource("provedorEnderecosInvalidos")
    public void criacaoEntidadeDominio_DeveLancarExcecao_QuandoCamposEnderecoPrincipaisSaoNulos(
            String pais, String estado, String cidade, String bairro, String endereco) {
        assertThatThrownBy(() -> new EnderecoCompleto(pais, estado, cidade, bairro, endereco));
    }
}