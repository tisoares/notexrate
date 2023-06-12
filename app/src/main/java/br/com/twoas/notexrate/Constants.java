package br.com.twoas.notexrate;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;

import java.util.Locale;
import java.util.UUID;

/**
 * Created by tiSoares on 09/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class Constants {
    public static final String DATABASE_NAME = "notexrate-db";
    public static final String WDG_IDENTIFIER = "WDG_DATA_IDENTIFIER";
    public static final String CURRENCY_IDENTIFIER = "CURRENCY_DATA_IDENTIFIER";
    public static final String  WDG_DATA = "WDG_DATA_VALUE";
    public static final String CLICK_EVENT = "WDG_CLICK";
    public static final int SECONDS_INTERVAL = 60;
    public static final long MILLI_INTERVAL = 1000 * SECONDS_INTERVAL; // Millisec * Second * Minute
    public static final String ACTIVITY_ID = UUID.randomUUID().toString();
    public static final Integer DEFAULT_WDG = -1;

    public static final String DATE_COMPLETE_FORMAT = "EEEE dd MMMM yyyy HH:mm.SSS O";
    @SuppressLint("ConstantLocale")
    public static final SimpleDateFormat COMPETE_DATE_FORMATER = new SimpleDateFormat(DATE_COMPLETE_FORMAT, Locale.getDefault());
    private Constants () {}
}
