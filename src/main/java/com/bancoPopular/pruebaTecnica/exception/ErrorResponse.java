package com.bancoPopular.pruebaTecnica.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class ErrorResponse {

    int estado;
    Date fecha;
    List<String> errores;
    String mensaje;

    public ErrorResponse() {
    }

    public ErrorResponse(String mensaje) {
        this.mensaje = mensaje;
    }

}
