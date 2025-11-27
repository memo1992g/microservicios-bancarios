package com.bank.cuenta.service;

import com.bank.cuenta.dto.EstadoCuentaResponse;

import java.time.LocalDate;

public interface ReporteService {
    EstadoCuentaResponse generarEstadoCuenta(String clienteId, LocalDate desde, LocalDate hasta);
}
