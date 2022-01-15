package com.bancoPopular.pruebaTecnica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecursoNoEncontradoException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public RecursoNoEncontradoException() {}

    public RecursoNoEncontradoException(String message) {
        super(message);
    }
}
