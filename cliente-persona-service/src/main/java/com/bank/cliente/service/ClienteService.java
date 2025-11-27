package com.bank.cliente.service;

import com.bank.cliente.model.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente crearCliente(Cliente cliente);
    List<Cliente> listarClientes();

    Cliente obtenerClientePorId(Long id);

    Cliente actualizarCliente(Long id, Cliente clienteActualizado);

    void eliminarCliente(Long id);
}
