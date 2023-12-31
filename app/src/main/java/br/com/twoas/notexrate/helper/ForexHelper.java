package br.com.twoas.notexrate.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.List;
import java.util.stream.Collectors;

import br.com.twoas.notexrate.Constants;
import br.com.twoas.notexrate.database.AppDatabase;
import br.com.twoas.notexrate.domain.executor.impl.ThreadExecutor;
import br.com.twoas.notexrate.domain.interactors.impl.DeleteWidgetRemovedInteractorImpl;
import br.com.twoas.notexrate.domain.interactors.impl.ProcessQuotesInteractorImpl;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.network.RestClient;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import br.com.twoas.notexrate.network.services.GetForexDataService;
import br.com.twoas.notexrate.presentation.model.WidgetData;
import br.com.twoas.notexrate.presentation.ui.widget.NotexrateWidget;
import br.com.twoas.notexrate.receiver.ForexAlarmReceiver;
import br.com.twoas.notexrate.threading.MainThreadImpl;
import br.com.twoas.notexrate.utils.JsonUtils;

/**
 * Created by tiSoares on 13/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class ForexHelper {
    private static ForexHelper instance;

    private ForexHelper() {

    }

    public static synchronized ForexHelper getInstance() {
        if (instance == null){
            instance =  new ForexHelper();
        }
        return instance;
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
        int[] ids = getWidgetIds(context);
        intentWdg.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        intentWdg.putExtra(Constants.WDG_DATA, JsonUtils.toJsonList(WidgetData.class, data));
        context.sendBroadcast(intentWdg);
    }

    public int[] getWidgetIds(Context context) {
        return AppWidgetManager.getInstance(context.getApplicationContext())
                .getAppWidgetIds(new ComponentName(context.getApplicationContext(), NotexrateWidget.class));
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
                        List<WidgetData> data = convertToWidgetData(currencyNotifies);
                        updateWidget(context, data);
                        new NotificationHelper().processNotifications(context, data);
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
        return new WidgetData( currency.uid,
                currency.wdgId,
                currency.label,
                currency.lastPrice,
                currency.isDown(),
                currency.isMinAlarming(),
                currency.isMaxAlarming());
    }


    public void deleteRemovedWdg(Context context) {
        new DeleteWidgetRemovedInteractorImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                () -> {},
                AppDatabase.getAppDatabase(context).currencyNotifyRepository(),
                getWidgetIds(context)
        ).execute();
    }

}
