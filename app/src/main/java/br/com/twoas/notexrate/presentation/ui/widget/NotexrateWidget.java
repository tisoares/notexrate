package br.com.twoas.notexrate.presentation.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import br.com.twoas.notexrate.Constants;
import br.com.twoas.notexrate.R;
import br.com.twoas.notexrate.presentation.ui.activities.MainActivity;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class NotexrateWidget extends AppWidgetProvider {


    private String value = "5,13245";

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Timber.d("Widget id: %d", appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.notexrate_widget);
        views.setTextViewText(R.id.amount, value );
        views.setOnClickPendingIntent(R.id.wdg,
                getPendingSelfIntent(context, Constants.CLICK_EVENT+appWidgetId, ""+appWidgetId));
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
        if (intent.getAction().startsWith(Constants.CLICK_EVENT)) {
            Timber.d("Clicou %s", intent.getExtras().getString(Constants.DATA_IDENTIFIER));
            openActivity(context, intent.getExtras().getString(Constants.DATA_IDENTIFIER));
        }
        if(intent.getStringExtra("price") != null) {
            value = intent.getStringExtra("price");
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
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Constants.DATA_IDENTIFIER, identifier);
        context.startActivity(intent);
    }
}