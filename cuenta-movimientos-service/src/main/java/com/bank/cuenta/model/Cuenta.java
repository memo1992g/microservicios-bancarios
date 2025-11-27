package com.bank.cuenta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Cuenta {

    @Id
    private String numeroCuenta;

    private String tipoCuenta;
    @Column(precision = 19, scale = 2)
    private BigDecimal saldoInicial;
    private Boolean estado;
    private String clienteId;
}
