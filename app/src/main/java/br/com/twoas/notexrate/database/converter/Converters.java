package br.com.twoas.notexrate.database.converter;

import androidx.room.TypeConverter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tiSoares on 09/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class Converters {
    @TypeConverter
    public BigDecimal fromLong(Long value) {
        return value == null ? null : new BigDecimal(value).divide(new BigDecimal(100));
    }

    @TypeConverter
    public Long toLong(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        } else {
            return bigDecimal.multiply(new BigDecimal(100)).longValue();
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
