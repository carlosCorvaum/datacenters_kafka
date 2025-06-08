package com.datacenter;

public class SensorData {
    public String id_sensor;
    public String location;
    public double temperature;
    public String timestamp;

    public SensorData(String id_sensor, String location, double temperature, String timestamp) {
        this.id_sensor = id_sensor;
        this.location = location;
        this.temperature = temperature;
        this.timestamp = timestamp;
    }

    // Jackson precisa de construtor vazio
    public SensorData() {}
} 