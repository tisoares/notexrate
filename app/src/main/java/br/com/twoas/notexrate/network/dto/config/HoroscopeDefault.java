package br.com.twoas.notexrate.network.dto.config;

import com.squareup.moshi.Json;

public class HoroscopeDefault {
    @Json(name = "properties")
    private HoroscopeProperty property;

    public HoroscopeDefault(HoroscopeProperty property) {
        this.property = property;
    }

    public HoroscopeProperty getProperty() {
        return property;
    }

    public void setProperty(HoroscopeProperty property) {
        this.property = property;
    }
}
