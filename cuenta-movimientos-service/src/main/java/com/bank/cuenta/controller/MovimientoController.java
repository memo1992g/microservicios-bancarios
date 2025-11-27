package com.bank.cuenta.controller;

import com.bank.cuenta.model.Movimiento;
import com.bank.cuenta.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService service;

    // REGISTRAR MOVIMIENTO EN UNA CUENTA
    @PostMapping("/{numeroCuenta}")
    public ResponseEntity<Movimiento> registrar(
            @PathVariable String numeroCuenta,
            @RequestBody Movimiento mov) {

        return ResponseEntity.ok(service.registrarMovimiento(numeroCuenta, mov));
    }

    // LISTAR TODOS LOS MOVIMIENTOS
    @GetMapping
    public ResponseEntity<List<Movimiento>> listar() {
        return ResponseEntity.ok(service.listarMovimientos());
    }

    // OBTENER MOVIMIENTO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // ELIMINAR MOVIMIENTO POR ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}
