package com.marcos.sensorapp.controllers;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.marcos.sensorapp.domain.SensorDataResponse;
import com.marcos.sensorapp.domain.SensorOutput;
import com.marcos.sensorapp.services.MockDataSource;
import com.marcos.sensorapp.services.SensorData;

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
                    try {
                        String temperatureMessage = processedTemperature(parseDouble(data.temperature()));
                        String luminosityMessage = processedLuminosity(parseDouble(data.luminosity()));
                        String humidityMessage = processedHumidity(parseDouble(data.humidity()));

                        SensorDataResponse dataResponse = new SensorDataResponse(temperatureMessage, luminosityMessage,
                                humidityMessage);

                        messagingTemplate.convertAndSend("/main/params", dataResponse);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 2000);
    }

    private String processedTemperature(double temperatura) {
        if (temperatura < 10) {
            return "Temperatura: " + temperatura + "°C. Está muito frio, recomenda-se usar roupas pesadas.";
        } else if (temperatura >= 10 && temperatura < 20) {
            return "Temperatura: " + temperatura + "°C. Um pouco frio, é bom usar uma blusa.";
        } else if (temperatura >= 20 && temperatura < 25) {
            return "Temperatura: " + temperatura + "°C. Está agradável, clima ideal para atividades externas.";
        } else if (temperatura >= 25 && temperatura < 30) {
            return "Temperatura: " + temperatura + "°C. Um clima quente, confortável para a maioria das pessoas.";
        } else if (temperatura >= 30 && temperatura < 35) {
            return "Temperatura: " + temperatura
                    + "°C. Está quente, recomenda-se ligar o ventilador ou ar condicionado.";
        } else if (temperatura >= 35 && temperatura < 40) {
            return "Temperatura: " + temperatura
                    + "°C. Muito quente, cuidado com desidratação, use ventilação ou ar condicionado.";
        } else if (temperatura >= 40 && temperatura < 50) {
            return "Temperatura: " + temperatura
                    + "°C. Extremamente quente, recomenda-se evitar sair ao sol por longos períodos.";
        } else if (temperatura >= 50 && temperatura < 60) {
            return "Temperatura: " + temperatura
                    + "°C. Temperatura perigosa, recomenda-se não permanecer em ambientes quentes por muito tempo.";
        } else if (temperatura >= 60) {
            return "Temperatura: " + temperatura
                    + "°C. Perigo extremo, risco de incêndio e queimaduras. Procure imediatamente um local seguro e com ventilação.";
        }

        return "Temperatura acima do valor máximo do sensor.";
    }

    // Função para recomendar algo baseado na luminosidade
    private String processedLuminosity(double luminosidade) {
        if (luminosidade < 50) {
            return "Está muito escuro. Recomenda-se ligar as luzes, ideal para dormir.";
        } else if (luminosidade >= 50 && luminosidade < 150) {
            return "Está um pouco escuro. Boa luminosidade para relaxar ou assistir TV.";
        } else if (luminosidade >= 150 && luminosidade < 250) {
            return "Luminosidade adequada. Boa para atividades como ler ou trabalhar.";
        } else if (luminosidade >= 250 && luminosidade < 350) {
            return "Luminosidade alta. Ideal para tarefas detalhadas ou usar eletrônicos.";
        } else if (luminosidade >= 350 && luminosidade <= 471) {
            return "Está muito claro. Pode ser desconfortável para os olhos em ambientes internos.";
        } else {
            return "Valor de luminosidade fora do intervalo esperado.";
        }
    }

    // Função para recomendar algo baseado na umidade
    private String processedHumidity(double umidade) {
        if (umidade < 20) {
            return "Umidade muito baixa, pode ser desconfortável e causar ressecamento da pele e das vias respiratórias.";
        } else if (umidade >= 20 && umidade < 30) {
            return "Umidade baixa, pode ser desconfortável, especialmente em ambientes internos.";
        } else if (umidade >= 30 && umidade < 40) {
            return "Umidade um pouco baixa, talvez seja necessário umidificar o ambiente.";
        } else if (umidade >= 40 && umidade < 60) {
            return "Níveis de umidade confortáveis, ideal para a maioria das atividades.";
        } else if (umidade >= 60 && umidade < 70) {
            return "Umidade um pouco alta, pode começar a ficar desconfortável para algumas pessoas.";
        } else if (umidade >= 70 && umidade < 80) {
            return "Umidade alta, cuidado com mofo e desconforto no ambiente.";
        } else {
            return "Umidade muito alta, risco de mofo e sensação de abafamento.";
        }
    }

    private double parseDouble(String value) throws ParseException {
        DecimalFormat df = new DecimalFormat();
        return df.parse(value).doubleValue();
    }
}
