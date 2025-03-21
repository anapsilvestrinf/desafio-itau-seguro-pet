package com.itau.localizacao_api.infra.controller;

import com.itau.localizacao_api.core.exceptions.Exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class HandlerException {
    @ExceptionHandler(Exceptions.PosicaoSensorInvalida.class)
    public ResponseEntity<Object> handle(Exceptions.PosicaoSensorInvalida exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(Exceptions.EnderecoNaoEncontrado.class)
    public ResponseStatusException handle(Exceptions.EnderecoNaoEncontrado exception) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
    }
}
