package br.com.twoas.notexrate.presentation.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.com.twoas.notexrate.database.AppDatabase;
import br.com.twoas.notexrate.databinding.ActivityMainBinding;
import br.com.twoas.notexrate.domain.executor.impl.ThreadExecutor;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.domain.repository.CurrencyNotifyRepository;
import br.com.twoas.notexrate.network.RestClient;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import br.com.twoas.notexrate.presentation.presenters.MainPresenter;
import br.com.twoas.notexrate.presentation.presenters.impl.MainPresenterImpl;
import br.com.twoas.notexrate.receiver.ForexAlarmReceiver;
import br.com.twoas.notexrate.threading.MainThreadImpl;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    private ActivityMainBinding binding;
    private MainPresenter mPresenter;
    private ForexAlarmReceiver alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarm = new ForexAlarmReceiver();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mPresenter = new MainPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                RestClient.getService(GetConfigDataService.class),
                this);

        binding.label.setOnClickListener(this::onLabelClick);
        alarm.updateWidget(this);
        alarm.cancelAlarm(this);
        alarm.setAlarm(this);
        alarm.processQuotes(this);
//        clearAll();
    }

    public void onLabelClick(View v) {
        Timber.d("Label Clicked");
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    private void clearAll() {
        CurrencyNotifyRepository repository =  AppDatabase
                .getAppDatabase(this)
                .currencyNotifyRepository();
        List<CurrencyNotify> currencies = repository.getAll();
        for (CurrencyNotify currency: currencies) {
            repository.delete(currency);
        }
    }
}
