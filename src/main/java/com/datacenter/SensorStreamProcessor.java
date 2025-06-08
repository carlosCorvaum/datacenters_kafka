package com.datacenter;

import io.confluent.kafka.serializers.KafkaJsonDeserializer;
import io.confluent.kafka.serializers.KafkaJsonSerializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;

import java.util.Map;
import java.util.Properties;

public class SensorStreamProcessor {

    private static final String BOOTSTRAP_SERVERS = "127.0.0.1:9092";
    private static final String TOPIC = "data-center-temperatures-streams1";

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "sensor-streams-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

        // Key = String, Value = SensorData (JSON)
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        // A gente vai definir o value serde no builder (opcional setar aqui)

        StreamsBuilder builder = new StreamsBuilder();

        // Configura o deserializer para SensorData
        KafkaJsonDeserializer<SensorData> deserializer = new KafkaJsonDeserializer<>();
        deserializer.configure(
            Map.of("json.value.type", SensorData.class.getName()),
            false  // false porque é para o value (true seria para key)
        );

        // Precisa de serializer para completar o serde, mesmo que não usemos aqui
        KafkaJsonSerializer<SensorData> serializer = new KafkaJsonSerializer<>();

        Serde<SensorData> sensorDataSerde = Serdes.serdeFrom(serializer, deserializer);

        KStream<String, SensorData> stream = builder.stream(
            TOPIC,
            Consumed.with(
                Serdes.String(),
                sensorDataSerde
            )
        );

        stream.foreach((key, data) -> {
            System.out.printf("Sensor: %s | Local: %s | Temp: %.2f | Hora: %s%n",
                data.id_sensor, data.location, data.temperature, data.timestamp);
            if (data.temperature > 80.0) {
                System.out.println("ALERTA: Temperatura crítica detectada!");
            }
        });

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
