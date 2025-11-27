package com.bank.cuenta.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private String tipo;     // DEPOSITO | RETIRO

    @Column(precision = 19, scale = 2)
    private BigDecimal valor;

    @Column(precision = 19, scale = 2)
    private BigDecimal saldo;

    @ManyToOne
    @JoinColumn(name = "numero_cuenta")
    private Cuenta cuenta;
}

