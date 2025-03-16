package com.marcos.sensorapp.utils;

import java.text.DecimalFormat;
import java.text.ParseException;

public class ProcessedTemperature {
        public static String processed(String sTemperature) {

                double temperature = parseDouble(sTemperature);

                RangeProcessor.Range[] ranges = {
                                new RangeProcessor.Range(-Double.MIN_VALUE, 10, "Temperatura: " + temperature
                                                + "°C. Está muito frio, recomenda-se usar roupas pesadas."),
                                new RangeProcessor.Range(10, 20,
                                                "Temperatura: " + temperature
                                                                + "°C. Um pouco frio, é bom usar uma blusa."),
                                new RangeProcessor.Range(20, 25, "Temperatura: " + temperature
                                                + "°C. Está agradável, clima ideal para atividades externas."),
                                new RangeProcessor.Range(25, 30, "Temperatura: " + temperature
                                                + "°C. Um clima quente, confortável para a maioria das pessoas."),
                                new RangeProcessor.Range(30, 35, "Temperatura: " + temperature
                                                + "°C. Está quente, recomenda-se ligar o ventilador ou ar condicionado."),
                                new RangeProcessor.Range(35, 40, "Temperatura: " + temperature
                                                + "°C. Muito quente, cuidado com desidratação, use ventilação ou ar condicionado."),
                                new RangeProcessor.Range(40, 50, "Temperatura: " + temperature
                                                + "°C. Extremamente quente, recomenda-se evitar sair ao sol por longos períodos."),
                                new RangeProcessor.Range(50, 60, "Temperatura: " + temperature
                                                + "°C. Temperatura perigosa, recomenda-se não permanecer em ambientes quentes por muito tempo."),
                                new RangeProcessor.Range(60, Double.MAX_VALUE, "Temperatura: " + temperature
                                                + "°C. Perigo extremo, risco de incêndio e queimaduras. Procure imediatamente um local seguro e com ventilação.")
                };

                for (RangeProcessor.Range range : ranges) {
                        if (temperature >= range.min && temperature < range.max) {
                                return range.message;
                        }
                }

                return "Temperatura acima do valor máximo do sensor.";
        }

        private static double parseDouble(String value) {
                DecimalFormat df = new DecimalFormat();
                try {
                        return df.parse(value).doubleValue();
                } catch (ParseException e) {
                        return Double.NaN;
                }
        }
}
