package br.com.twoas.notexrate.domain.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tiSoares on 09/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
@Entity(tableName = "currency_notify",
 indices = {
        @Index(value = {"wdg_id"}, unique = true)
})
public class CurrencyNotify {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "code")
    public String code;
    @ColumnInfo(name = "label")
    public String label;
    @ColumnInfo(name = "interval_update")
    public long intervalUpdate;
    @ColumnInfo(name = "min_valuer_alert")
    public BigDecimal minValueAlert;
    @ColumnInfo(name = "max_value_alert")
    public BigDecimal maxValueAlert;
    @ColumnInfo(name = "wdg_id")
    public int wdgId;
    @ColumnInfo(name = "last_price")
    public BigDecimal lastPrice;
    @ColumnInfo(name = "last_update")
    public Date lastUpdate;
    @ColumnInfo(name = "last_price_change")
    public BigDecimal lastPriceChange;
}
