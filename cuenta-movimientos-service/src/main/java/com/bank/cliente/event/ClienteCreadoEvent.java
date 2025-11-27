package com.bank.cliente.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCreadoEvent {
    private String clienteId;
    private String nombre;
    private String genero;
}
