package com.marcos.sensorapp.utils;

import java.text.DecimalFormat;
import java.text.ParseException;

public class ProcessedLuminosity {
    public static String processed(String sLuminosity) {
        double luminosity = parseDouble(sLuminosity);

        RangeProcessor.Range[] ranges = {
                new RangeProcessor.Range(Double.MIN_VALUE, 50, "Está muito escuro. Ideal para dormir."),
                new RangeProcessor.Range(50, 150,
                        "Está um pouco escuro. Boa luminosidade para relaxar ou assistir TV."),
                new RangeProcessor.Range(150, 250, "Luminosidade adequada. Boa para atividades como ler ou trabalhar."),
                new RangeProcessor.Range(250, 350,
                        "Luminosidade alta. Ideal para tarefas detalhadas ou usar eletrônicos."),
                new RangeProcessor.Range(350, 471,
                        "Está muito claro. Pode ser desconfortável para os olhos em ambientes internos.")
        };

        for (RangeProcessor.Range range : ranges) {
            if (luminosity >= range.min && luminosity < range.max) {
                return range.message;
            }
        }

        return "Valor de luminosidade fora do intervalo esperado.";
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
