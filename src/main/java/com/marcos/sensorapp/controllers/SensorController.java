package com.marcos.sensorapp.controllers;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.marcos.sensorapp.domain.SensorDataResponse;
import com.marcos.sensorapp.domain.SensorOutput;
import com.marcos.sensorapp.services.MockDataSource;
import com.marcos.sensorapp.services.SensorData;
import com.marcos.sensorapp.utils.ProcessedHumidity;
import com.marcos.sensorapp.utils.ProcessedLuminosity;
import com.marcos.sensorapp.utils.ProcessedTemperature;

import jakarta.annotation.PostConstruct;

@Controller
public class SensorController {

    private final SimpMessagingTemplate messagingTemplate;
    private final SensorData sensorData = new MockDataSource();

    public SensorController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostConstruct
    public void startSensorDataReading() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SensorOutput data = sensorData.readSensorData();

                if (data != null) {
                    String temperatureMessage = ProcessedTemperature.processed(data.temperature());
                    String luminosityMessage = ProcessedLuminosity.processed(data.luminosity());
                    String humidityMessage = ProcessedHumidity.processed(data.humidity());

                    SensorDataResponse dataResponse = new SensorDataResponse(temperatureMessage, luminosityMessage,
                            humidityMessage);

                    messagingTemplate.convertAndSend("/main/params", dataResponse);
                }
            }
        }, 0, 2000);
    }
}
