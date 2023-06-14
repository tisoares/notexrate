package br.com.twoas.notexrate.utils;

import java.math.BigDecimal;
import java.util.Date;

import br.com.twoas.notexrate.Constants;

/**
 * Created by tiSoares on 11/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class Utils {
    private Utils(){}

    public static String bigDecimalToString(BigDecimal bd) {
        if (bd == null) {
            return "0.0000";
        }
        return bd.toString();
    }

    public static String formatDate(Date date){
        if (date == null) {
            return "";
        }
        return Constants.COMPETE_DATE_FORMATTER.format(date);
    }
}
