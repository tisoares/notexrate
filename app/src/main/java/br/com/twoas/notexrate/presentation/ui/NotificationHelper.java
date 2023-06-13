package br.com.twoas.notexrate.presentation.ui;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.List;

import br.com.twoas.notexrate.Constants;
import br.com.twoas.notexrate.R;
import br.com.twoas.notexrate.presentation.model.WidgetData;
import br.com.twoas.notexrate.presentation.ui.activities.CurrencyDetailActivity;
import br.com.twoas.notexrate.utils.Utils;

/**
 * Created by tiSoares on 13/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class NotificationHelper {

    private static final String CHANNEL_ID = "notexrate_chanel_id";
    private static final String CHANNEL_NAME = "Notexrate";
    private static final String CHANNEL_DESCRIPTION = "Notexrate exchange alert";

    public void processNotifications(Context context, List<WidgetData> data) {
        for (WidgetData wdg: data) {
            if ((wdg.isMaxAlarming() || wdg.isMinAlarming()) && isBackgroundRunning(context)) {
                makeAndShowNotification(context, wdg);
            }
        }
    }

    private void makeAndShowNotification(Context context, WidgetData data) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel (required for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);
            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getTitle(data))
                .setContentText(getContentText(data))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(getIntent(context, data.getUid()))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true); // Remove the notification when the user taps on it

        // Show the notification
        notificationManager.notify(data.getUid(), builder.build());
    }

    private String getAlertType(WidgetData data) {
        String alertType;
        if (data.isMinAlarming()) {
            alertType = "Down";
        } else if (data.isMaxAlarming()) {
            alertType = "Up";
        } else {
            alertType = "";
        }
        return alertType;
    }

    private String getTitle(WidgetData data) {
        return data.getLabel()+" "+getAlertType(data);
    }

    private String getContentText(WidgetData data) {
        return getAlertType(data)+" "+data.getLabel()+" = "+ Utils.bigDecimalToString(data.getPrice());
    }

    private PendingIntent getIntent(Context context, int extra) {
        Intent intent = new Intent(context, CurrencyDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Constants.CURRENCY_IDENTIFIER, ""+extra);
        return PendingIntent.getActivity(context, extra, intent, PendingIntent.FLAG_MUTABLE);
    }

    public static boolean isBackgroundRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(context.getPackageName())) {
                        //If your app is the process in foreground, then it's not in running in background
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
