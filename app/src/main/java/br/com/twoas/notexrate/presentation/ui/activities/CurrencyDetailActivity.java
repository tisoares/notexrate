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
import br.com.twoas.notexrate.helper.ForexHelper;
import br.com.twoas.notexrate.presentation.presenters.CurrencyDetailActivityPresenter;
import br.com.twoas.notexrate.presentation.presenters.impl.CurrencyDetailActivityPresenterImpl;
import br.com.twoas.notexrate.presentation.ui.fragment.CurrencyDetailFragment;
import br.com.twoas.notexrate.presentation.ui.fragment.SettingsWidgetFragment;
import br.com.twoas.notexrate.presentation.ui.viewmodel.CurrencyViewModel;
import br.com.twoas.notexrate.threading.MainThreadImpl;

public class CurrencyDetailActivity extends AppCompatActivity implements CurrencyDetailActivityPresenter.View,
        SettingsWidgetFragment.Listener, CurrencyDetailFragment.Listener {

    private CurrencyViewModel mViewModel;
    private CurrencyDetailActivityPresenter mPresenter;
    private Fragment mCurrentFragment;
    private ActivityCurrencyDetailBinding mBinding;
    private ForexHelper mForex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCurrencyDetailBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mViewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);
        mForex = ForexHelper.getInstance();
        mPresenter = new CurrencyDetailActivityPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), RestClient.getService(GetConfigDataService.class), this, mViewModel,
                AppDatabase.getAppDatabase(this.getApplicationContext()),
                RestClient.getService(GetForexDataService.class));

        if (savedInstanceState == null) {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().containsKey(Constants.WDG_IDENTIFIER)) {
                    mPresenter.setWidgetId(getIntent().getExtras().getString(Constants.WDG_IDENTIFIER));
                } else if (getIntent().getExtras().containsKey(Constants.CURRENCY_IDENTIFIER)) {
                    mPresenter.setCurrencyId(getIntent().getExtras().getString(Constants.CURRENCY_IDENTIFIER));
                }
            }
        }
    }

    @Override
    public void showProgress() {
        mBinding.groupView.setVisibility(View.INVISIBLE);
        mBinding.groupProcess.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mBinding.groupView.setVisibility(View.VISIBLE);
        mBinding.groupProcess.setVisibility(View.INVISIBLE);
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
        loadFragment(CurrencyDetailFragment.newInstance(this));
    }

    private void loadFragment(Fragment fragment) {
        mCurrentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(mBinding.container.getId(), mCurrentFragment)
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
        mForex.processQuotes(this);
        if (!currencyNotifies.isEmpty()) {
            mViewModel.currencyNotify = currencyNotifies.get(0);
            openDetailsFragment();
        }
    }

    @Override
    public void loadQuote() {
        if (isDetailFragment()) {
            ((CurrencyDetailFragment) mCurrentFragment).loadData();
        }
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

    private boolean isDetailFragment() {
        return mCurrentFragment != null && mCurrentFragment instanceof CurrencyDetailFragment;
    }

    @Override
    public void onRefresh() {
        mForex.processQuotes(this);
        mPresenter.refreshQuote();
    }

    @Override
    public void onEdit() {
        openSettingsFragment();
    }
}