package com.bank.cliente.event;

import com.bank.cliente.model.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClienteProducer {

    private final KafkaTemplate<String, ClienteCreadoEvent> kafkaTemplate;

    public void enviarClienteCreado(Cliente cliente) {

        ClienteCreadoEvent event = new ClienteCreadoEvent(
                cliente.getClienteId(),
                cliente.getNombre(),
                cliente.getGenero()
        );

        kafkaTemplate.send("cliente-creado", event);

        log.info(" Evento enviado a Kafka: {}", event);
    }
}
