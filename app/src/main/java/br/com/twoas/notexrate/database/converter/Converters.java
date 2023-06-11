package br.com.twoas.notexrate.database.converter;

import androidx.room.TypeConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * Created by tiSoares on 09/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class Converters {

    private static final int DEFAULT_DIVIDE = 10000;

    @TypeConverter
    public BigDecimal fromLong(Long value) {
        return value == null ? null : new BigDecimal(value).divide(new BigDecimal(DEFAULT_DIVIDE));
    }

    @TypeConverter
    public Long toLong(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        } else {
            return bigDecimal.multiply(new BigDecimal(DEFAULT_DIVIDE)).longValue();
        }
    }

    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}
