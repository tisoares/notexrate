package br.com.twoas.notexrate.network.dto.forex;

import com.squareup.moshi.Json;

public class CurrencyDTO {

    @Json(name = "CountryCode")
    private String countryCode;
    @Json(name = "CountryName")
    private String countryName;
    @Json(name = "CurrencyCode")
    private String currencyCode;
    @Json(name = "CurrencyName")
    private String currencyName;
    @Json(name = "CurrencySymbol")
    private String currencySymbol;

    // GETTERS && SETTERS

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
}
