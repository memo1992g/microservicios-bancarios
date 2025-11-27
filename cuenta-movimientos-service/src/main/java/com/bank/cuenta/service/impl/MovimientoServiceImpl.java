package com.bank.cuenta.service.impl;

import com.bank.cuenta.exception.CuentaNoEncontradaException;
import com.bank.cuenta.exception.MovimientoNoEncontradoException;
import com.bank.cuenta.exception.SaldoNoDisponibleException;
import com.bank.cuenta.model.Cuenta;
import com.bank.cuenta.model.Movimiento;
import com.bank.cuenta.repository.CuentaRepository;
import com.bank.cuenta.repository.MovimientoRepository;
import com.bank.cuenta.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final CuentaRepository cuentaRepo;
    private final MovimientoRepository movRepo;

    @Override
    public Movimiento registrarMovimiento(String numeroCuenta, Movimiento mov) {

        Cuenta cuenta = cuentaRepo.findById(numeroCuenta)
                .orElseThrow(() -> new CuentaNoEncontradaException("Cuenta no encontrada"));

        // SUMA CORRECTA EN BIGDECIMAL
        BigDecimal nuevoSaldo = cuenta.getSaldoInicial().add(mov.getValor());

        // VALIDACIÓN PROFESIONAL
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoNoDisponibleException("Saldo no disponible");
        }

        mov.setFecha(LocalDate.now());
        mov.setCuenta(cuenta);
        mov.setSaldo(nuevoSaldo);

        // ACTUALIZA LA CUENTA
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepo.save(cuenta);

        return movRepo.save(mov);
    }

    @Override
    public List<Movimiento> listarMovimientos() {
        return movRepo.findAll();
    }

    @Override
    public Movimiento obtenerPorId(Long id) {
        return movRepo.findById(id)
                .orElseThrow(() -> new MovimientoNoEncontradoException("Movimiento no encontrado"));
    }

    @Override
    public void eliminarMovimiento(Long id) {
        Movimiento mov = obtenerPorId(id);
        movRepo.delete(mov);
    }
}
