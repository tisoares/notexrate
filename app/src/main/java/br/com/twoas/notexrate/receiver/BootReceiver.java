package br.com.twoas.notexrate.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.com.twoas.notexrate.helper.ForexHelper;

/**
 * Broadcast to start services
 * <p>
 * Created by tiSoares on 08/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            ForexHelper.getInstance().setAlarm(context);
        }
    }
}