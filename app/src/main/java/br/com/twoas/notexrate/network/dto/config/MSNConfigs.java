package br.com.twoas.notexrate.network.dto.config;

import com.squareup.moshi.Json;

public class MSNConfigs {
    @Json(name = "shared/msn-ns/HoroscopeAnswerCardWC/default")
    private HoroscopeDefault horoscopeDefault;

    public MSNConfigs(HoroscopeDefault horoscopeDefault) {
        this.horoscopeDefault = horoscopeDefault;
    }

    public HoroscopeDefault getHoroscopeDefault() {
        return horoscopeDefault;
    }

    public void setHoroscopeDefault(HoroscopeDefault horoscopeDefault) {
        this.horoscopeDefault = horoscopeDefault;
    }
}
