package com.itau.localizacao_api.core.exceptions;

public class Exceptions extends Exception {

    public static class PosicaoSensorInvalida extends RuntimeException {
        public PosicaoSensorInvalida(String message) {
            super(message);
        }
    }

    public static class EnderecoNaoEncontrado extends RuntimeException {
        public EnderecoNaoEncontrado(String message) {
            super(message);
        }
    }
}
