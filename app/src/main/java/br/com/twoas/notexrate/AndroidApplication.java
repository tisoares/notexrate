package br.com.twoas.notexrate;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by TIAGO SOARES on 16/07/2019.
 */
public class AndroidApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // initiate Timber
        Timber.plant(new Timber.DebugTree());
    }
}
