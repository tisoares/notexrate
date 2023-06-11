package br.com.twoas.notexrate.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast to start services
 * <p>
 * Created by tiSoares on 08/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class BootReceiver extends BroadcastReceiver {

    private ForexAlarmReceiver alarm = new ForexAlarmReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        alarm.setAlarm(context);
    }
}