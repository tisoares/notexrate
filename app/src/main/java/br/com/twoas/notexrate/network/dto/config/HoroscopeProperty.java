package br.com.twoas.notexrate.network.dto.config;

import com.squareup.moshi.Json;

public class HoroscopeProperty {

    @Json(name = "horoscopeAnswerServiceClientSettings")
    private HoroscopeSettings horoscopeAnswerServiceClientSettings;

    public HoroscopeProperty(HoroscopeSettings horoscopeAnswerServiceClientSettings) {
        this.horoscopeAnswerServiceClientSettings = horoscopeAnswerServiceClientSettings;
    }

    public HoroscopeSettings getHoroscopeAnswerServiceClientSettings() {
        return horoscopeAnswerServiceClientSettings;
    }

    public void setHoroscopeAnswerServiceClientSettings(HoroscopeSettings horoscopeAnswerServiceClientSettings) {
        this.horoscopeAnswerServiceClientSettings = horoscopeAnswerServiceClientSettings;
    }

}
