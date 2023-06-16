package br.com.twoas.notexrate.network.dto.forex;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tiSoares on 16/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class ChartDataDTO {

    private ChartSeries series;
    private BigDecimal pricePreviousClose;
    private String chartType;
    private String exchangeId;
    private String displayName;
    private String shortName;
    private String securityType;
    private String instrumentId;
    private String symbol;
    private Date timeLastUpdated;

    public ChartDataDTO(ChartSeries series,
                        BigDecimal pricePreviousClose,
                        String chartType,
                        String exchangeId,
                        String displayName,
                        String shortName,
                        String securityType,
                        String instrumentId,
                        String symbol,
                        Date timeLastUpdated) {
        this.series = series;
        this.pricePreviousClose = pricePreviousClose;
        this.chartType = chartType;
        this.exchangeId = exchangeId;
        this.displayName = displayName;
        this.shortName = shortName;
        this.securityType = securityType;
        this.instrumentId = instrumentId;
        this.symbol = symbol;
        this.timeLastUpdated = timeLastUpdated;
    }

    // GETTERS $$ SETTERS

    public ChartSeries getSeries() {
        return series;
    }

    public void setSeries(ChartSeries series) {
        this.series = series;
    }

    public BigDecimal getPricePreviousClose() {
        return pricePreviousClose;
    }

    public void setPricePreviousClose(BigDecimal pricePreviousClose) {
        this.pricePreviousClose = pricePreviousClose;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public void setTimeLastUpdated(Date timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
    }
}
