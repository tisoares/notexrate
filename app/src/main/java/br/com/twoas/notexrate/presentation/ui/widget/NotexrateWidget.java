package br.com.twoas.notexrate.presentation.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import br.com.twoas.notexrate.R;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class NotexrateWidget extends AppWidgetProvider {
    private static final String CLICK_EVENT = "WDG_CLICK";
    private static final String  DATA_IDENTIFIER = "WDG_DATA_IDENTIFIER";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Timber.d("Widget id: %d", appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.notexrate_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setOnClickPendingIntent(R.id.wdg,
                getPendingSelfIntent(context, NotexrateWidget.CLICK_EVENT+appWidgetId, ""+appWidgetId));
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
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
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().startsWith(CLICK_EVENT)) {
            Timber.d("Clicou %s", intent.getExtras().getString(DATA_IDENTIFIER));
        }
    }

    private static PendingIntent getPendingSelfIntent(Context context, String action, String identifier) {
        Intent intent = new Intent(context, NotexrateWidget.class);
        intent.setAction(action);
        intent.putExtra(NotexrateWidget.DATA_IDENTIFIER, identifier);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE);
    }
}