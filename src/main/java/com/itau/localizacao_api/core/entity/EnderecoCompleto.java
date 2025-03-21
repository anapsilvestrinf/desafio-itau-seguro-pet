package com.itau.localizacao_api.core.entity;

import com.itau.localizacao_api.core.exceptions.Exceptions;

public record EnderecoCompleto(
        String pais,
        String estado,
        String cidade,
        String bairro,
        String endereco) {

    public EnderecoCompleto {
        requireNonNullOrEmpty(pais);
        requireNonNullOrEmpty(estado);
        requireNonNullOrEmpty(cidade);
    }

    public static void requireNonNullOrEmpty(String value) {
        if (value == null || value.isEmpty()) {
            throw new Exceptions.EnderecoNaoEncontrado("Não foi possível encontrar um endereço completo válido.");
        }
    }
}