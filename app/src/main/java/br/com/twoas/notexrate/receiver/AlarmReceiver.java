package br.com.twoas.notexrate.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import br.com.twoas.notexrate.Constants;
import br.com.twoas.notexrate.domain.executor.impl.ThreadExecutor;
import br.com.twoas.notexrate.domain.interactors.MSNConfigInteractor;
import br.com.twoas.notexrate.domain.interactors.QuoteInteractor;
import br.com.twoas.notexrate.domain.interactors.impl.MSNConfigInteractorImpl;
import br.com.twoas.notexrate.domain.interactors.impl.QuoteInteractorImpl;
import br.com.twoas.notexrate.network.RestClient;
import br.com.twoas.notexrate.network.dto.config.HoroscopeSettings;
import br.com.twoas.notexrate.network.dto.forex.QuoteDTO;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import br.com.twoas.notexrate.network.services.GetForexDataService;
import br.com.twoas.notexrate.presentation.ui.widget.NotexrateWidget;
import br.com.twoas.notexrate.threading.MainThreadImpl;
import timber.log.Timber;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String apiKey = "0QfOX3Vn51YCzitbLaRkTTBadtWpgTN8NZLW0C1SEM";

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        getQuotes(context);
    }

    public void updateWidget(Context context, String price) {
        Intent intentWdg = new Intent(context, NotexrateWidget.class);
        intentWdg.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context.getApplicationContext())
                .getAppWidgetIds(new ComponentName(context.getApplicationContext(), NotexrateWidget.class));
        intentWdg.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        intentWdg.putExtra("price", price);
        context.sendBroadcast(intentWdg);
    }

    public void setAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_MUTABLE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), Constants.MILLI_INTERVAL, pi);
    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    private void getApiKey() {
        new MSNConfigInteractorImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                new MSNConfigInteractor.Callback() {
                    @Override
                    public void onMSNConfigSuccess(HoroscopeSettings settings) {
//                        apiKey = settings.getApikey();
                    }

                    @Override
                    public void onMSNConfigFail(String error) {
                        // Do noting
                    }
                },
                RestClient.getConfigService(GetConfigDataService.class)).execute();

    }

    public void getQuotes(Context context) {
        new QuoteInteractorImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                new QuoteInteractor.Callback() {
                    @Override
                    public void onQuoteSuccess(List<QuoteDTO> quotes) {
                        if (!quotes.isEmpty()) {
                            updateWidget(context, quotes.get(0).getPrice().toString());
                        }
                    }

                    @Override
                    public void onQuoteFail(String error) {
                        Timber.e(error);
                    }
                },
                RestClient.getService(GetForexDataService.class),
                apiKey,
                Constants.ACTIVITY_ID,
                Collections.singletonList("av8tf2")).execute();
    }
}