package com.aluracursos.conversor.modelos;

import java.util.Map;

public class MonedaDTO {
    private String baseCode;
    private Map<String, Double> conversionRates;

    public MonedaDTO() {
    }

    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }

    public void setConversionRates(Map<String, Double> conversionRates) {
        this.conversionRates = conversionRates;
    }
}
