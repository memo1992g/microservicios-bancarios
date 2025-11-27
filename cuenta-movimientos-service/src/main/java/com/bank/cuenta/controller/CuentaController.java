package com.bank.cuenta.controller;

import com.bank.cuenta.model.Cuenta;
import com.bank.cuenta.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService service;

    // POST: crear cuenta
    @PostMapping
    public ResponseEntity<Cuenta> crear(@RequestBody Cuenta cuenta) {
        return ResponseEntity.ok(service.crearCuenta(cuenta));
    }

    // GET: listar todas las cuentas
    @GetMapping
    public ResponseEntity<List<Cuenta>> listar() {
        return ResponseEntity.ok(service.listarCuentas());
    }

    // GET: obtener cuenta por número
    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<Cuenta> obtenerCuenta(@PathVariable String numeroCuenta) {
        return ResponseEntity.ok(service.obtenerCuenta(numeroCuenta));
    }

    // PUT: actualizar cuenta
    @PutMapping("/{numeroCuenta}")
    public ResponseEntity<Cuenta> actualizar(
            @PathVariable String numeroCuenta,
            @RequestBody Cuenta cuenta) {
        return ResponseEntity.ok(service.actualizarCuenta(numeroCuenta, cuenta));
    }

    // DELETE: eliminar cuenta
    @DeleteMapping("/{numeroCuenta}")
    public ResponseEntity<Void> eliminar(@PathVariable String numeroCuenta) {
        service.eliminarCuenta(numeroCuenta);
        return ResponseEntity.noContent().build();
    }
}
