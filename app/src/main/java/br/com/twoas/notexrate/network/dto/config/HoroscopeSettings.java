package br.com.twoas.notexrate.network.dto.config;


import com.squareup.moshi.Json;

public class HoroscopeSettings {
    @Json(name = "apikey")
    private String apikey;
    @Json(name = "serviceHost")
    private String serviceHost;

    public HoroscopeSettings(String apikey, String serviceHost){
        this.apikey = apikey;
        this.serviceHost = serviceHost;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getServiceHost() {
        return serviceHost;
    }

    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }
}
