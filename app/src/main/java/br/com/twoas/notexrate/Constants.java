package br.com.twoas.notexrate;

import java.util.UUID;

/**
 * Created by tiSoares on 09/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class Constants {
    public static final String DATABASE_NAME = "notexrate-db";
    public static final String  DATA_IDENTIFIER = "WDG_DATA_IDENTIFIER";
    public static final String CLICK_EVENT = "WDG_CLICK";
    public static final int SECONDS_INTERVAL = 60;
    public static final long MILLI_INTERVAL = 1000 * SECONDS_INTERVAL; // Millisec * Second * Minute
    public static final String ACTIVITY_ID = UUID.randomUUID().toString();

    private Constants () {}
}
