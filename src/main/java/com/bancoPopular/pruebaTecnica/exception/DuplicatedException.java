package com.bancoPopular.pruebaTecnica.exception;

public class DuplicatedException extends RuntimeException {

    public DuplicatedException() {
        super();
    }

    public DuplicatedException(String message) {
        super(message);
    }

}
