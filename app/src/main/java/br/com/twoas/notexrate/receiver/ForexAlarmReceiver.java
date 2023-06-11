package br.com.twoas.notexrate.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import br.com.twoas.notexrate.Constants;
import br.com.twoas.notexrate.database.AppDatabase;
import br.com.twoas.notexrate.domain.executor.impl.ThreadExecutor;
import br.com.twoas.notexrate.domain.interactors.impl.ProcessQuotesInteractorImpl;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.network.RestClient;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import br.com.twoas.notexrate.network.services.GetForexDataService;
import br.com.twoas.notexrate.presentation.model.WidgetData;
import br.com.twoas.notexrate.presentation.ui.widget.NotexrateWidget;
import br.com.twoas.notexrate.threading.MainThreadImpl;
import br.com.twoas.notexrate.utils.JsonUtils;

public class ForexAlarmReceiver extends BroadcastReceiver {

    public ForexAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        processQuotes(context);
    }


    public void updateWidget(Context context) {
        new Thread(() ->
            updateWidget(context,
                    convertToWidgetData(AppDatabase
                            .getAppDatabase(context)
                            .currencyNotifyRepository()
                            .getAll()))
        ).start();
    }
    public void updateWidget(Context context, List<WidgetData> data) {
        Intent intentWdg = new Intent(context, NotexrateWidget.class);
        intentWdg.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context.getApplicationContext())
                .getAppWidgetIds(new ComponentName(context.getApplicationContext(), NotexrateWidget.class));
        intentWdg.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        intentWdg.putExtra(Constants.WDG_DATA, JsonUtils.toJsonList(WidgetData.class, data));
        context.sendBroadcast(intentWdg);
    }

    public void setAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, ForexAlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_MUTABLE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), Constants.MILLI_INTERVAL, pi);
    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, ForexAlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void processQuotes(Context context) {
        new ProcessQuotesInteractorImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                RestClient.getService(GetConfigDataService.class),
                currencyNotifies -> {
                    if (!currencyNotifies.isEmpty()) {
                        updateWidget(context, convertToWidgetData(currencyNotifies));
                    }
                },
                AppDatabase.getAppDatabase(context).currencyNotifyRepository(),
                RestClient.getService(GetForexDataService.class)).execute();
    }

    private List<WidgetData> convertToWidgetData(List<CurrencyNotify> currencies) {
        return currencies.stream()
                .map(this::convertToWidgetData)
                .collect(Collectors.toList());
    }

    private WidgetData convertToWidgetData(CurrencyNotify currency) {
        return new WidgetData(currency.wdgId,
                currency.label,
                currency.lastPrice,
                BigDecimal.ZERO.compareTo(currency.lastPriceChange) >= 0,
                false); // TODO: Sinalize alert
    }
}