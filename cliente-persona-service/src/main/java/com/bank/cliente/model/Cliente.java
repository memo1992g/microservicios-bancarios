package com.bank.cliente.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Cliente extends Persona {

    @Column(unique = true)
    private String clienteId;
    private String contrasena;
    private Boolean estado;
}
