package com.marcos.sensorapp.services;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.marcos.sensorapp.domain.SensorOutput;

import jakarta.annotation.PostConstruct;

@Service
public class MockDataSource implements SensorData {
    private final Random random = new Random();

    private SensorOutput generateRandomData() {
        return new SensorOutput(
                String.format("%.2f", random.nextDouble() * 180),
                String.format("%.2f", random.nextDouble() * 471),
                String.format("%.2f", random.nextDouble() * 100));
    }

    @PostConstruct
    public SensorOutput readSensorData() {
        SensorOutput data = generateRandomData();
        return data;
    }
}
