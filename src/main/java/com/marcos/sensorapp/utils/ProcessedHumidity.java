package com.marcos.sensorapp.utils;

import java.text.DecimalFormat;
import java.text.ParseException;

public class ProcessedHumidity {
    public static String processed(String sHumidity) {
        double humidity = parseDouble(sHumidity);

        RangeProcessor.Range[] ranges = {
                new RangeProcessor.Range(Double.MIN_VALUE, 20, "Umidade em " + humidity
                        + "%. Baixa umidade pode ser perigosa, causando ressecamento da pele e vias respiratórias. Use umidificadores e hidrate-se."),
                new RangeProcessor.Range(20, 30,
                        "Umidade em " + humidity + "%. Pode ser desconfortável, especialmente em ambientes internos."),
                new RangeProcessor.Range(30, 40,
                        "Umidade em " + humidity + "%. Talvez seja necessário umidificar o ambiente."),
                new RangeProcessor.Range(40, 60, "Umidade em " + humidity + "%. Ideal para a maioria das atividades."),
                new RangeProcessor.Range(60, 70,
                        "Umidade em " + humidity + "%. Pode começar a ficar desconfortável para algumas pessoas."),
                new RangeProcessor.Range(70, 80,
                        "Umidade em " + humidity + "%. Cuidado com mofo e desconforto no ambiente.")
        };

        for (RangeProcessor.Range range : ranges) {
            if (humidity >= range.min && humidity < range.max) {
                return range.message;
            }
        }

        return "Umidade muito alta, risco de mofo e sensação de abafamento.";
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
