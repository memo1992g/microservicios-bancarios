package com.bank.cuenta.service;

import com.bank.cuenta.model.Cuenta;

import java.util.List;

public interface CuentaService {

    Cuenta crearCuenta(Cuenta cuenta);

    Cuenta obtenerCuenta(String numeroCuenta);

    List<Cuenta> listarCuentas();

    Cuenta actualizarCuenta(String numeroCuenta, Cuenta cuenta);

    void eliminarCuenta(String numeroCuenta);
}
