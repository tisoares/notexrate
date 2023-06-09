package br.com.twoas.notexrate.presentation.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import br.com.twoas.notexrate.Constants;
import br.com.twoas.notexrate.databinding.ActivityMainBinding;
import br.com.twoas.notexrate.domain.executor.impl.ThreadExecutor;
import br.com.twoas.notexrate.presentation.presenters.MainPresenter;
import br.com.twoas.notexrate.presentation.presenters.impl.MainPresenterImpl;
import br.com.twoas.notexrate.receiver.AlarmReceiver;
import br.com.twoas.notexrate.threading.MainThreadImpl;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    private ActivityMainBinding binding;
    private MainPresenter mPresenter;

    private AlarmReceiver alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarm = new AlarmReceiver();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mPresenter = new MainPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);

        if (getIntent().getExtras()!= null &&  getIntent().getExtras().containsKey(Constants.DATA_IDENTIFIER)){
            binding.label.setText(getIntent().getExtras().getString(Constants.DATA_IDENTIFIER));
        }
        binding.label.setOnClickListener(this::onLabelClick);
        alarm.cancelAlarm(this);
        alarm.setAlarm(this);
        alarm.getQuotes(this);
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
}
