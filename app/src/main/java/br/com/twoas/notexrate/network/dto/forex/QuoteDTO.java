package br.com.twoas.notexrate.network.dto.forex;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class QuoteDTO {
    private BigDecimal price; // Important
    private BigDecimal priceChange; // Important
    private BigDecimal priceDayHigh;
    private BigDecimal priceDayLow;
    private String fromCurrency;
    private LocalDateTime timeLastTraded;
    private BigDecimal priceDayOpen;
    private BigDecimal pricePreviousClose;
    private LocalDateTime datePreviousClose;
    private BigDecimal priceAsk;
    private BigDecimal askSize;
    private BigDecimal priceBid;
    private BigDecimal bidSize;
    private BigDecimal accumulatedVolume;
    private BigDecimal priceChangePercent;
    private BigDecimal price52wHigh;
    private BigDecimal price52wLow;
    private BigDecimal priceChange1Week;
    private BigDecimal priceChange1Month;
    private BigDecimal priceChange3Month;
    private BigDecimal priceChange6Month;
    private BigDecimal priceChangeYTD;
    private BigDecimal priceChange1Year;
    private BigDecimal return1Week;
    private BigDecimal return1Month;
    private BigDecimal return3Month;
    private BigDecimal return6Month;
    private BigDecimal returnYTD;
    private BigDecimal return1Year;
    private String sourceExchangeCode;
    private String sourceExchangeName;
    private String exchangeId;
    private String exchangeCode;
    private String exchangeName;
    private String displayName;
    private String instrumentId; //Important
    private String symbol; // EURBRL
    private String friendlySymbol; // EUR/BRL
    private LocalDateTime timeLastUpdated;
    private String currency;

    //GETTERS && SETTERS

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(BigDecimal priceChange) {
        this.priceChange = priceChange;
    }

    public BigDecimal getPriceDayHigh() {
        return priceDayHigh;
    }

    public void setPriceDayHigh(BigDecimal priceDayHigh) {
        this.priceDayHigh = priceDayHigh;
    }

    public BigDecimal getPriceDayLow() {
        return priceDayLow;
    }

    public void setPriceDayLow(BigDecimal priceDayLow) {
        this.priceDayLow = priceDayLow;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public LocalDateTime getTimeLastTraded() {
        return timeLastTraded;
    }

    public void setTimeLastTraded(LocalDateTime timeLastTraded) {
        this.timeLastTraded = timeLastTraded;
    }

    public BigDecimal getPriceDayOpen() {
        return priceDayOpen;
    }

    public void setPriceDayOpen(BigDecimal priceDayOpen) {
        this.priceDayOpen = priceDayOpen;
    }

    public BigDecimal getPricePreviousClose() {
        return pricePreviousClose;
    }

    public void setPricePreviousClose(BigDecimal pricePreviousClose) {
        this.pricePreviousClose = pricePreviousClose;
    }

    public LocalDateTime getDatePreviousClose() {
        return datePreviousClose;
    }

    public void setDatePreviousClose(LocalDateTime datePreviousClose) {
        this.datePreviousClose = datePreviousClose;
    }

    public BigDecimal getPriceAsk() {
        return priceAsk;
    }

    public void setPriceAsk(BigDecimal priceAsk) {
        this.priceAsk = priceAsk;
    }

    public BigDecimal getAskSize() {
        return askSize;
    }

    public void setAskSize(BigDecimal askSize) {
        this.askSize = askSize;
    }

    public BigDecimal getPriceBid() {
        return priceBid;
    }

    public void setPriceBid(BigDecimal priceBid) {
        this.priceBid = priceBid;
    }

    public BigDecimal getBidSize() {
        return bidSize;
    }

    public void setBidSize(BigDecimal bidSize) {
        this.bidSize = bidSize;
    }

    public BigDecimal getAccumulatedVolume() {
        return accumulatedVolume;
    }

    public void setAccumulatedVolume(BigDecimal accumulatedVolume) {
        this.accumulatedVolume = accumulatedVolume;
    }

    public BigDecimal getPriceChangePercent() {
        return priceChangePercent;
    }

    public void setPriceChangePercent(BigDecimal priceChangePercent) {
        this.priceChangePercent = priceChangePercent;
    }

    public BigDecimal getPrice52wHigh() {
        return price52wHigh;
    }

    public void setPrice52wHigh(BigDecimal price52wHigh) {
        this.price52wHigh = price52wHigh;
    }

    public BigDecimal getPrice52wLow() {
        return price52wLow;
    }

    public void setPrice52wLow(BigDecimal price52wLow) {
        this.price52wLow = price52wLow;
    }

    public BigDecimal getPriceChange1Week() {
        return priceChange1Week;
    }

    public void setPriceChange1Week(BigDecimal priceChange1Week) {
        this.priceChange1Week = priceChange1Week;
    }

    public BigDecimal getPriceChange1Month() {
        return priceChange1Month;
    }

    public void setPriceChange1Month(BigDecimal priceChange1Month) {
        this.priceChange1Month = priceChange1Month;
    }

    public BigDecimal getPriceChange3Month() {
        return priceChange3Month;
    }

    public void setPriceChange3Month(BigDecimal priceChange3Month) {
        this.priceChange3Month = priceChange3Month;
    }

    public BigDecimal getPriceChange6Month() {
        return priceChange6Month;
    }

    public void setPriceChange6Month(BigDecimal priceChange6Month) {
        this.priceChange6Month = priceChange6Month;
    }

    public BigDecimal getPriceChangeYTD() {
        return priceChangeYTD;
    }

    public void setPriceChangeYTD(BigDecimal priceChangeYTD) {
        this.priceChangeYTD = priceChangeYTD;
    }

    public BigDecimal getPriceChange1Year() {
        return priceChange1Year;
    }

    public void setPriceChange1Year(BigDecimal priceChange1Year) {
        this.priceChange1Year = priceChange1Year;
    }

    public BigDecimal getReturn1Week() {
        return return1Week;
    }

    public void setReturn1Week(BigDecimal return1Week) {
        this.return1Week = return1Week;
    }

    public BigDecimal getReturn1Month() {
        return return1Month;
    }

    public void setReturn1Month(BigDecimal return1Month) {
        this.return1Month = return1Month;
    }

    public BigDecimal getReturn3Month() {
        return return3Month;
    }

    public void setReturn3Month(BigDecimal return3Month) {
        this.return3Month = return3Month;
    }

    public BigDecimal getReturn6Month() {
        return return6Month;
    }

    public void setReturn6Month(BigDecimal return6Month) {
        this.return6Month = return6Month;
    }

    public BigDecimal getReturnYTD() {
        return returnYTD;
    }

    public void setReturnYTD(BigDecimal returnYTD) {
        this.returnYTD = returnYTD;
    }

    public BigDecimal getReturn1Year() {
        return return1Year;
    }

    public void setReturn1Year(BigDecimal return1Year) {
        this.return1Year = return1Year;
    }

    public String getSourceExchangeCode() {
        return sourceExchangeCode;
    }

    public void setSourceExchangeCode(String sourceExchangeCode) {
        this.sourceExchangeCode = sourceExchangeCode;
    }

    public String getSourceExchangeName() {
        return sourceExchangeName;
    }

    public void setSourceExchangeName(String sourceExchangeName) {
        this.sourceExchangeName = sourceExchangeName;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getFriendlySymbol() {
        return friendlySymbol;
    }

    public void setFriendlySymbol(String friendlySymbol) {
        this.friendlySymbol = friendlySymbol;
    }

    public LocalDateTime getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public void setTimeLastUpdated(LocalDateTime timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
