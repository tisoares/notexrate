package br.com.twoas.notexrate.network.dto.forex;

import com.squareup.moshi.Json;

public class IdMapDTO {

    private String mStartId;
    @Json(name = "guid")
    private String externalCode;
    private String type;
    private String exchangeId;
    private String symbol;
    private Integer status;

    // GETTERS && SETTERS

    public String getmStartId() {
        return mStartId;
    }

    public void setmStartId(String mStartId) {
        this.mStartId = mStartId;
    }

    public String getExternalCode() {
        return externalCode;
    }

    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
