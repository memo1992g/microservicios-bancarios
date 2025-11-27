package com.bank.cuenta.service.impl;

import com.bank.cuenta.model.Cuenta;
import com.bank.cuenta.repository.CuentaRepository;
import com.bank.cuenta.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository repository;

    @Override
    public Cuenta crearCuenta(Cuenta cuenta) {
        return repository.save(cuenta);
    }

    @Override
    public Cuenta obtenerCuenta(String numeroCuenta) {
        return repository.findById(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    @Override
    public List<Cuenta> listarCuentas() {
        return repository.findAll();
    }

    @Override
    public Cuenta actualizarCuenta(String numeroCuenta, Cuenta cuenta) {
        Cuenta existing = obtenerCuenta(numeroCuenta);

        existing.setTipoCuenta(cuenta.getTipoCuenta());
        existing.setSaldoInicial(cuenta.getSaldoInicial());
        existing.setEstado(cuenta.getEstado());

        return repository.save(existing);
    }

    @Override
    public void eliminarCuenta(String numeroCuenta) {
        Cuenta cuenta = obtenerCuenta(numeroCuenta);
        repository.delete(cuenta);
    }
}
