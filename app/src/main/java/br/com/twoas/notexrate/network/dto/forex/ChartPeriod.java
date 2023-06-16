package br.com.twoas.notexrate.network.dto.forex;

/**
 * Created by tiSoares on 16/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public enum ChartPeriod {
    DAY,
    THREE_DAYS,
    MONTH,
    THREE_MONTHS,
    YEAR,
    ALL;

    private String strToRequest;

    static {
        DAY.strToRequest = "1D1M";
        THREE_DAYS.strToRequest = "3D";
        MONTH.strToRequest = "1M";
        THREE_MONTHS.strToRequest = "3M";
        YEAR.strToRequest = "1Y";
        ALL.strToRequest = "ALl";
    }

    public String getPeriodToRequest() {
        return strToRequest;
    }
}
