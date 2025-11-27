package com.bank.cliente.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void debeCrearClienteCorrectamente() {

        Cliente cliente = new Cliente();
        cliente.setClienteId("CLI100");
        cliente.setContrasena("1234");
        cliente.setEstado(true);

        cliente.setNombre("Guillermo");
        cliente.setGenero("M");
        cliente.setEdad(30);
        cliente.setIdentificacion("12345678-9");
        cliente.setDireccion("San Salvador");
        cliente.setTelefono("77777777");

        assertEquals("CLI100", cliente.getClienteId());
        assertEquals("Guillermo", cliente.getNombre());
        assertTrue(cliente.getEstado());
        assertEquals("M", cliente.getGenero());
        assertEquals(30, cliente.getEdad());
    }
}
