package br.com.twoas.notexrate.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.com.twoas.notexrate.helper.ForexHelper;

public class ForexAlarmReceiver extends BroadcastReceiver {

    public ForexAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ForexHelper forex = ForexHelper.getInstance();
        forex.deleteRemovedWdg(context);
        forex.processQuotes(context);
    }
}
