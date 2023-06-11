package br.com.twoas.notexrate.presentation.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.math.BigDecimal;
import java.util.List;

import br.com.twoas.notexrate.Constants;
import br.com.twoas.notexrate.database.AppDatabase;
import br.com.twoas.notexrate.databinding.ActivityCurrencyDetailBinding;
import br.com.twoas.notexrate.domain.executor.impl.ThreadExecutor;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.network.RestClient;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import br.com.twoas.notexrate.network.services.GetForexDataService;
import br.com.twoas.notexrate.presentation.presenters.CurrencyDetailActivityPresenter;
import br.com.twoas.notexrate.presentation.presenters.impl.CurrencyDetailActivityPresenterImpl;
import br.com.twoas.notexrate.presentation.ui.fragment.CurrencyDetailFragment;
import br.com.twoas.notexrate.presentation.ui.fragment.SettingsWidgetFragment;
import br.com.twoas.notexrate.presentation.ui.viewmodel.CurrencyViewModel;
import br.com.twoas.notexrate.receiver.ForexAlarmReceiver;
import br.com.twoas.notexrate.threading.MainThreadImpl;

public class CurrencyDetailActivity extends AppCompatActivity implements CurrencyDetailActivityPresenter.View,
        SettingsWidgetFragment.Listener {

    private CurrencyViewModel mViewModel;
    private CurrencyDetailActivityPresenter mPresenter;
    private Fragment mCurrentFragment;
    private ActivityCurrencyDetailBinding mBiding;
    private ForexAlarmReceiver mAlarme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBiding = ActivityCurrencyDetailBinding.inflate(getLayoutInflater());
        setContentView(mBiding.getRoot());
        mViewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);
        mAlarme = new ForexAlarmReceiver();
        mPresenter = new CurrencyDetailActivityPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), RestClient.getService(GetConfigDataService.class), this, mViewModel,
                AppDatabase.getAppDatabase(this.getApplicationContext()),
                RestClient.getService(GetForexDataService.class));

        if (savedInstanceState == null) {
            if (getIntent().getExtras()!= null &&  getIntent().getExtras().containsKey(Constants.DATA_IDENTIFIER)){
               mPresenter.setWidgetId(getIntent().getExtras().getString(Constants.DATA_IDENTIFIER));
            }
        }
    }

    @Override
    public void showProgress() {
        mBiding.groupView.setVisibility(View.INVISIBLE);
        mBiding.groupProcess.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mBiding.groupView.setVisibility(View.VISIBLE);
        mBiding.groupProcess.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openSettingsFragment() {
        loadFragment(SettingsWidgetFragment.newInstance(this));
    }

    @Override
    public void openDetailsFragment() {
        loadFragment(CurrencyDetailFragment.newInstance());
    }

    private void loadFragment(Fragment fragment) {
        mCurrentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(mBiding.container.getId(), mCurrentFragment)
                .commit();
    }

    @Override
    public void currencyCode(String code) {
        if (isSettingsFragment()){
            ((SettingsWidgetFragment) mCurrentFragment).save(code);
        }
    }

    @Override
    public void wrongCurrency() {
        if (isSettingsFragment()){
            ((SettingsWidgetFragment) mCurrentFragment).wrongCurrencies();
        }
    }

    @Override
    public void saved(List<CurrencyNotify> currencyNotifies) {
        mAlarme.processQuotes(this);
        finish();
    }

    @Override
    public void onSave(String code,String from, String to, BigDecimal min, BigDecimal max) {
        mPresenter.saveCurrencyNotify(code, from, to, min, max);
    }

    @Override
    public void onValidateCurrency(String from, String to) {
        mPresenter.getCurrencyCode(from, to);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.widgetId = 0;
        mCurrentFragment = null;
    }

    private boolean isSettingsFragment() {
        return mCurrentFragment != null && mCurrentFragment instanceof SettingsWidgetFragment;
    }
}