package com.bank.cuenta.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class EstadoCuentaResponse {

    private String clienteId;
    private List<CuentaReporteDto> cuentas;

    @Data
    public static class CuentaReporteDto {
        private String numeroCuenta;
        private String tipoCuenta;
        private Boolean estado;
        private BigDecimal saldoActual;      // ← MISMO TIPO QUE saldoInicial
        private List<MovimientoDto> movimientos;
    }

    @Data
    public static class MovimientoDto {
        private LocalDate fecha;             // ← MISMO TIPO QUE en Movimiento
        private String tipo;
        private BigDecimal valor;            // ← MISMO TIPO QUE en Movimiento
        private BigDecimal saldo;            // ← MISMO TIPO QUE en Movimiento
    }
}
