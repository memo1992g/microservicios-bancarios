package com.bank.cuenta.service;

import com.bank.cuenta.model.Movimiento;

import java.util.List;

public interface MovimientoService {
    Movimiento registrarMovimiento(String numeroCuenta, Movimiento mov);
    List<Movimiento> listarMovimientos();

    Movimiento obtenerPorId(Long id);

    void eliminarMovimiento(Long id);


}

