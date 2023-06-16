package br.com.twoas.notexrate.network.dto.forex;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by tiSoares on 16/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class ChartSeries {

    private List<BigDecimal> openPrices;
    private List<BigDecimal> prices;
    private List<BigDecimal> pricesHigh;
    private List<BigDecimal> pricesLow;
    private BigDecimal priceHigh;
    private BigDecimal priceLow;
    private List<BigDecimal> volumes;
    private List<Date> timeStamps;
    private Date startTime;
    private Date endTime;

    public ChartSeries(List<BigDecimal> openPrices,
                       List<BigDecimal> prices,
                       List<BigDecimal> pricesHigh,
                       List<BigDecimal> pricesLow,
                       BigDecimal priceHigh,
                       BigDecimal priceLow,
                       List<BigDecimal> volumes,
                       List<Date> timeStamps,
                       Date startTime,
                       Date endTime) {
        this.openPrices = openPrices;
        this.prices = prices;
        this.pricesHigh = pricesHigh;
        this.pricesLow = pricesLow;
        this.priceHigh = priceHigh;
        this.priceLow = priceLow;
        this.volumes = volumes;
        this.timeStamps = timeStamps;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // GETTERS && SETTERS

    public List<BigDecimal> getOpenPrices() {
        return openPrices;
    }

    public void setOpenPrices(List<BigDecimal> openPrices) {
        this.openPrices = openPrices;
    }

    public List<BigDecimal> getPrices() {
        return prices;
    }

    public void setPrices(List<BigDecimal> prices) {
        this.prices = prices;
    }

    public List<BigDecimal> getPricesHigh() {
        return pricesHigh;
    }

    public void setPricesHigh(List<BigDecimal> pricesHigh) {
        this.pricesHigh = pricesHigh;
    }

    public List<BigDecimal> getPricesLow() {
        return pricesLow;
    }

    public void setPricesLow(List<BigDecimal> pricesLow) {
        this.pricesLow = pricesLow;
    }

    public BigDecimal getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(BigDecimal priceHigh) {
        this.priceHigh = priceHigh;
    }

    public BigDecimal getPriceLow() {
        return priceLow;
    }

    public void setPriceLow(BigDecimal priceLow) {
        this.priceLow = priceLow;
    }

    public List<BigDecimal> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<BigDecimal> volumes) {
        this.volumes = volumes;
    }

    public List<Date> getTimeStamps() {
        return timeStamps;
    }

    public void setTimeStamps(List<Date> timeStamps) {
        this.timeStamps = timeStamps;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
