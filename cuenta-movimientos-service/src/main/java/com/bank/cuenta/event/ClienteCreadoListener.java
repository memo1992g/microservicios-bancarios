package com.bank.cuenta.event;

import com.bank.cliente.event.ClienteCreadoEvent;
import com.bank.cuenta.model.Cuenta;
import com.bank.cuenta.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClienteCreadoListener {

    private final CuentaRepository cuentaRepository;

    @KafkaListener(topics = "cliente-creado", groupId = "cuentas-group")
    public void recibirClienteCreado(ClienteCreadoEvent event) {

        log.info(" Evento recibido desde Kafka: {}", event);

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("ACC-" + event.getClienteId());
        cuenta.setClienteId(event.getClienteId());
        cuenta.setSaldoInicial(BigDecimal.ZERO);

        cuenta.setEstado(true);
        cuenta.setTipoCuenta("AHORRO");

        cuentaRepository.save(cuenta);

        log.info(" Cuenta creada para cliente {}", event.getClienteId());
    }
}
