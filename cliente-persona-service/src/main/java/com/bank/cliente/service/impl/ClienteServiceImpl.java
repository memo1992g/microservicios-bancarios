package com.bank.cliente.service.impl;

import com.bank.cliente.event.ClienteProducer;
import com.bank.cliente.model.Cliente;
import com.bank.cliente.repository.ClienteRepository;
import com.bank.cliente.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final ClienteProducer producer;

    @Override
    public Cliente crearCliente(Cliente cliente) {
        Cliente saved = repository.save(cliente);
        producer.enviarClienteCreado(saved);
        return saved;
    }

    @Override
    public List<Cliente> listarClientes() {
        return repository.findAll();
    }

    @Override
    public Cliente obtenerClientePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        Cliente cliente = obtenerClientePorId(id);

        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setGenero(clienteActualizado.getGenero());
        cliente.setEdad(clienteActualizado.getEdad());
        cliente.setIdentificacion(clienteActualizado.getIdentificacion());
        cliente.setDireccion(clienteActualizado.getDireccion());
        cliente.setTelefono(clienteActualizado.getTelefono());

        cliente.setClienteId(clienteActualizado.getClienteId());
        cliente.setContrasena(clienteActualizado.getContrasena());
        cliente.setEstado(clienteActualizado.getEstado());

        return repository.save(cliente);
    }

    @Override
    public void eliminarCliente(Long id) {
        Cliente cliente = obtenerClientePorId(id);
        repository.delete(cliente);
    }
}
