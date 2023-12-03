package com.mycompany.app;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ExchangeRateData {

    @JsonAlias("1. From_Currency Code")
    private String fromCurrencyCode;

    @JsonAlias("2. From_Currency Name")
    private String fromCurrencyName;

    @JsonAlias("3. To_Currency Code")
    private String toCurrencyCode;

    @JsonAlias("4. To_Currency Name")
    private String toCurrencyName;

    @JsonAlias("5. Exchange Rate")
    private String exchangeRate;

    @JsonAlias("6. Last Refreshed")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastRefreshed;

    @JsonAlias("7. Time Zone")
    private String timeZone;

    @JsonAlias("8. Bid Price")
    private String bidPrice;

    @JsonAlias("9. Ask Price")
    private String askPrice;

    // Getters and setters
    

    @Override
    public String toString() {
        return "ExchangeRateData{" +
                "fromCurrencyCode='" + fromCurrencyCode + '\'' +
                ", fromCurrencyName='" + fromCurrencyName + '\'' +
                ", toCurrencyCode='" + toCurrencyCode + '\'' +
                ", toCurrencyName='" + toCurrencyName + '\'' +
                ", exchangeRate='" + exchangeRate + '\'' +
                ", lastRefreshed=" + lastRefreshed +
                ", timeZone='" + timeZone + '\'' +
                ", bidPrice='" + bidPrice + '\'' +
                ", askPrice='" + askPrice + '\'' +
                '}';
    }

	public String getFromCurrencyCode() {
		return fromCurrencyCode;
	}

	public void setFromCurrencyCode(String fromCurrencyCode) {
		this.fromCurrencyCode = fromCurrencyCode;
	}

	public String getFromCurrencyName() {
		return fromCurrencyName;
	}

	public void setFromCurrencyName(String fromCurrencyName) {
		this.fromCurrencyName = fromCurrencyName;
	}

	public String getToCurrencyCode() {
		return toCurrencyCode;
	}

	public void setToCurrencyCode(String toCurrencyCode) {
		this.toCurrencyCode = toCurrencyCode;
	}

	public String getToCurrencyName() {
		return toCurrencyName;
	}

	public void setToCurrencyName(String toCurrencyName) {
		this.toCurrencyName = toCurrencyName;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public LocalDateTime getLastRefreshed() {
		return lastRefreshed;
	}

	public void setLastRefreshed(LocalDateTime lastRefreshed) {
		this.lastRefreshed = lastRefreshed;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(String bidPrice) {
		this.bidPrice = bidPrice;
	}

	public String getAskPrice() {
		return askPrice;
	}

	public void setAskPrice(String askPrice) {
		this.askPrice = askPrice;
	}
}
