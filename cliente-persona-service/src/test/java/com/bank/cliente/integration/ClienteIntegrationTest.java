package com.bank.cliente.integration;

import com.bank.cliente.model.Cliente;
import com.bank.cliente.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ClienteIntegrationTest {

    @Autowired
    private ClienteRepository repo;

    @Test
    void debeGuardarYRecuperarCliente() {

        // Crear entidad
        Cliente c = new Cliente();
        c.setClienteId("CLI100");

        c.setContrasena("abcd");
        c.setEstado(true);
        c.setNombre("Memo");
        c.setGenero("M");
        c.setEdad(25);
        c.setIdentificacion("1234567");
        c.setDireccion("San Salvador");
        c.setTelefono("70000000");

        // Guardar
        Cliente saved = repo.save(c);

        assertNotNull(saved.getId());
        assertEquals("CLI100", saved.getClienteId());

        // Recuperar usando el ID real (NO HARDCODEAR 1L)
        Cliente encontrado = repo.findById(saved.getId()).orElseThrow();

        assertNotNull(encontrado);
        assertEquals("Memo", encontrado.getNombre());
        assertEquals("CLI100", encontrado.getClienteId());
    }
}
