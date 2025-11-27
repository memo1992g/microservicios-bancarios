package com.bank.cuenta.exception;

public class MovimientoNoEncontradoException extends RuntimeException {

    public MovimientoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
