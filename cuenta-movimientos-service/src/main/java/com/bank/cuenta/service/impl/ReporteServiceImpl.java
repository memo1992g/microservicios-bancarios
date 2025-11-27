package com.bank.cuenta.service.impl;

import com.bank.cuenta.dto.EstadoCuentaResponse;
import com.bank.cuenta.model.Cuenta;
import com.bank.cuenta.model.Movimiento;
import com.bank.cuenta.repository.CuentaRepository;
import com.bank.cuenta.repository.MovimientoRepository;
import com.bank.cuenta.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final CuentaRepository cuentaRepo;
    private final MovimientoRepository movRepo;

    @Override
    public EstadoCuentaResponse generarEstadoCuenta(String clienteId, LocalDate desde, LocalDate hasta) {

        List<Cuenta> cuentas = cuentaRepo.findAll()
                .stream()
                .filter(c -> clienteId.equals(c.getClienteId()))
                .collect(Collectors.toList());

        EstadoCuentaResponse response = new EstadoCuentaResponse();
        response.setClienteId(clienteId);

        List<EstadoCuentaResponse.CuentaReporteDto> cuentasDto = cuentas.stream().map(c -> {

            EstadoCuentaResponse.CuentaReporteDto dto = new EstadoCuentaResponse.CuentaReporteDto();
            dto.setNumeroCuenta(c.getNumeroCuenta());
            dto.setTipoCuenta(c.getTipoCuenta());
            dto.setEstado(c.getEstado());
            dto.setSaldoActual(c.getSaldoInicial());

            // Movimientos filtrados por fecha y cuenta
            List<Movimiento> movimientos = movRepo.findAll().stream()
                    .filter(m -> m.getCuenta().getNumeroCuenta().equals(c.getNumeroCuenta()))
                    .filter(m -> !m.getFecha().isBefore(desde) && !m.getFecha().isAfter(hasta))
                    .collect(Collectors.toList());

            List<EstadoCuentaResponse.MovimientoDto> movDto = movimientos.stream().map(m -> {
                EstadoCuentaResponse.MovimientoDto md = new EstadoCuentaResponse.MovimientoDto();
                md.setFecha(m.getFecha());
                md.setTipo(m.getTipo());
                md.setValor(m.getValor());
                md.setSaldo(m.getSaldo());
                return md;
            }).collect(Collectors.toList());

            dto.setMovimientos(movDto);
            return dto;

        }).collect(Collectors.toList());

        response.setCuentas(cuentasDto);

        return response;
    }
}
