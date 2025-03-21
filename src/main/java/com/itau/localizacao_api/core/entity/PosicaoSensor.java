package com.itau.localizacao_api.core.entity;

import com.itau.localizacao_api.core.exceptions.Exceptions;

import java.time.LocalDateTime;

public record PosicaoSensor(
        Double latitude,
        Double longitude,
        LocalDateTime datahora) {

    public PosicaoSensor {
        requireNonNull(latitude, "Latitude inválida. O parâmetro é obrigatório para localização de um endereço");
        validaLimitesLatitude(latitude);

        requireNonNull(longitude, "Longitude inválida. O parâmetro é obrigatório para localização de um endereço");
        validaLimitesLongitude(longitude);
    }

    public static void requireNonNull(Double coordenada, String mensagemErro) {
        if (coordenada == null) {
            throw new Exceptions.PosicaoSensorInvalida(mensagemErro);
        }
    }

    public static void validaLimitesLatitude(Double latitude) {
        if (latitude < -90 || latitude > 90) {
            throw new Exceptions.PosicaoSensorInvalida("Latitude inválida. A latitude deve estar entre -90 e 90 graus.");
        }
    }

    public static void validaLimitesLongitude(Double longitude) {
        if (longitude < -180 || longitude > 180) {
            throw new Exceptions.PosicaoSensorInvalida("Longitude inválida. A Longitude deve estar entre -180 e 180 graus.");
        }
    }
}