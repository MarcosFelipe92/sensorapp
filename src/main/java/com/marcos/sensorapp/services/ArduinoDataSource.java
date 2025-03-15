package com.marcos.sensorapp.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.fazecast.jSerialComm.SerialPort;
import com.marcos.sensorapp.domain.SensorOutput;

public class ArduinoDataSource implements SensorData {
    private SerialPort serialPort;

    public SensorOutput readSensorData() {
        serialPort = SerialPort.getCommPort("COM3");
        serialPort.setBaudRate(9600);
        serialPort.setNumDataBits(8);
        serialPort.setNumStopBits(1);
        serialPort.setParity(SerialPort.NO_PARITY);
        serialPort.openPort();

        if (!serialPort.isOpen()) {
            System.err.println("❌ Erro ao abrir a porta serial!");
            return null;
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            String line = reader.readLine();

            if (line != null && !line.isEmpty()) {
                String[] values = line.split(",");
                if (values.length == 3) {
                    return new SensorOutput(
                            values[0],
                            values[1],
                            values[2]);
                }
            }
        } catch (Exception e) {
            System.err.println("⚠️ Erro na leitura dos dados: " + e.getMessage());
        }
        return null;
    }
}
