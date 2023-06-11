package br.com.twoas.notexrate.presentation.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import br.com.twoas.notexrate.Constants;
import br.com.twoas.notexrate.R;
import br.com.twoas.notexrate.database.AppDatabase;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.domain.repository.CurrencyNotifyRepository;
import br.com.twoas.notexrate.presentation.model.WidgetData;
import br.com.twoas.notexrate.presentation.ui.activities.CurrencyDetailActivity;
import br.com.twoas.notexrate.receiver.ForexAlarmReceiver;
import br.com.twoas.notexrate.utils.JsonUtils;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class NotexrateWidget extends AppWidgetProvider {

    private static List<WidgetData> mData;
    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Optional<WidgetData> wdg = getWidgetData(appWidgetId);
        Timber.d("Widget id: %d", appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.notexrate_widget);
        if (wdg.isPresent()) {
            views.setTextViewText(R.id.amount, wdg.get().getPrice().toString());
            views.setTextViewText(R.id.currency, wdg.get().getLabel());
            if (wdg.get().isDown()){
                views.setImageViewResource(R.id.indicator, R.drawable.ic_arrow_drop_down);
            } else {
                views.setImageViewResource(R.id.indicator, R.drawable.ic_arrow_drop_up);
            }
        } else {
            views.setTextViewText(R.id.amount, "0.00000");
            views.setTextViewText(R.id.currency, "UNDEFINED");
        }

        views.setOnClickPendingIntent(R.id.wdg,
                getPendingSelfIntent(context, Constants.CLICK_EVENT+appWidgetId, ""+appWidgetId));
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private Optional<WidgetData> getWidgetData(int appWidgetId) {
        if (mData == null) {
            return Optional.empty();
        }
        return mData.stream()
                .filter(w -> w.getWdgId().equals(appWidgetId))
                .findFirst();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        new ForexAlarmReceiver().setAlarm(context);
    }

    @Override
    public void onDisabled(Context context) {
        new ForexAlarmReceiver().setAlarm(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().startsWith(Constants.CLICK_EVENT)) {
            openActivity(context, intent.getExtras().getString(Constants.DATA_IDENTIFIER));
        }
        if(intent.getStringExtra(Constants.WDG_DATA) != null) {
            try {
                String json = intent.getStringExtra(Constants.WDG_DATA);
                mData = JsonUtils.fromList(WidgetData.class, json);
            } catch (IOException e) {
                Timber.e(e);
            }
        }
        super.onReceive(context, intent);
    }

    private static PendingIntent getPendingSelfIntent(Context context, String action, String identifier) {
        Intent intent = new Intent(context, NotexrateWidget.class);
        intent.setAction(action);
        intent.putExtra(Constants.DATA_IDENTIFIER, identifier);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE);
    }

    private static void openActivity(Context context, String identifier){
        Intent intent = new Intent(context, CurrencyDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Constants.DATA_IDENTIFIER, identifier);
        context.startActivity(intent);
    }
}