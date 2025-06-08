package com.datacenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import org.apache.kafka.clients.producer.*;

public class SensorProducer {

    private static final String BOOTSTRAP_SERVERS = "127.0.0.1:9092";
    private static final String TOPIC = "data-center-temperatures-streams1";

    public static void main(String[] args) throws Exception {
        // Timestamp
        String timestamp = ZonedDateTime.now(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        // Cria objeto
        SensorData data = new SensorData("sensor_002", "corridor_01", 30.5, timestamp);

        // Kafka config
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaJsonSerializer");
        try (KafkaProducer<String, SensorData> producer = new KafkaProducer<>(props)) {
            producer.send(new ProducerRecord<>(TOPIC, data.id_sensor, data));
            System.out.println("Mensagem enviada: " + data);
        }
    }
}
