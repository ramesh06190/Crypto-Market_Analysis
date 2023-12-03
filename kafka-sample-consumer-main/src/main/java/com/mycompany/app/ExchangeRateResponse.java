package com.mycompany.app;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateResponse {

    @JsonProperty("Realtime Currency Exchange Rate")
    private Map<String, ExchangeRateData> exchangeRateDataMap;

    private String message;

    public ExchangeRateData getExchangeRateData() {
        return exchangeRateDataMap != null ? exchangeRateDataMap.values().stream().findFirst().orElse(null) : null;
    }

    public String getMessage() {
        return message;
    }
}
