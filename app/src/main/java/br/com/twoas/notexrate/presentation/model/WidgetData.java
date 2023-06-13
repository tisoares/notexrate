package br.com.twoas.notexrate.presentation.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by tiSoares on 11/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class WidgetData implements Serializable {

    private Integer uid;
    private Integer wdgId;
    private String label;
    private BigDecimal price;
    private boolean down;
    private boolean minAlarming;
    private boolean maxAlarming;

    public WidgetData(Integer uid, Integer wdgId, String label, BigDecimal price, boolean down,
                      boolean minAlarming, boolean maxAlarming) {
        this.uid = uid;
        this.wdgId = wdgId;
        this.label = label;
        this.price = price;
        this.down = down;
        this.minAlarming = minAlarming;
        this.maxAlarming = maxAlarming;
    }

    // GETTERS && SETTERS

    public Integer getWdgId() {
        return wdgId;
    }

    public void setWdgId(Integer wdgId) {
        this.wdgId = wdgId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isMinAlarming() {
        return minAlarming;
    }

    public void setMinAlarming(boolean alarming) {
        this.minAlarming = alarming;
    }

    public boolean isMaxAlarming() {
        return maxAlarming;
    }

    public void setMaxAlarming(boolean maxAlarming) {
        this.maxAlarming = maxAlarming;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
