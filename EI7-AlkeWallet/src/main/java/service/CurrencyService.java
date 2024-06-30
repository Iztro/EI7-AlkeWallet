package service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CurrencyService {

    private Map<String, Double> exchangeRates;

    public CurrencyService() {
    	
    	exchangeRates.put("CLP", 1.00);
        exchangeRates.put("USD", 0.001063);
        exchangeRates.put("CAD", 0.001454);
        exchangeRates.put("EUR", 0.000994);
        exchangeRates.put("GBP", 0.000841);
        exchangeRates.put("BRL", 0.005947);
        exchangeRates.put("JPY", 0.171015);
        exchangeRates.put("AUD", 0.001594);
        exchangeRates.put("CHF", 0.000959);
        exchangeRates.put("CNY", 0.007725);
        exchangeRates.put("SEK", 0.007398);
        exchangeRates.put("NZD", 0.001746);
    }

    public Map<String, Double> getExchangeRates() {
        return exchangeRates;
    }

    public double convert(String fromCurrency, String toCurrency, double amount) {
        double fromRate = exchangeRates.getOrDefault(fromCurrency, 1.0);
        double toRate = exchangeRates.getOrDefault(toCurrency, 1.0);
        return amount * (toRate / fromRate);
    }
}
