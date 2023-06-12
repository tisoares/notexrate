package br.com.twoas.notexrate.presentation.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.twoas.notexrate.database.AppDatabase;
import br.com.twoas.notexrate.databinding.ActivityMainBinding;
import br.com.twoas.notexrate.domain.executor.impl.ThreadExecutor;
import br.com.twoas.notexrate.network.RestClient;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import br.com.twoas.notexrate.presentation.presenters.MainPresenter;
import br.com.twoas.notexrate.presentation.presenters.impl.MainPresenterImpl;
import br.com.twoas.notexrate.presentation.ui.recycleview.adapter.CurrencyAdapter;
import br.com.twoas.notexrate.presentation.ui.viewmodel.MainViewModel;
import br.com.twoas.notexrate.receiver.ForexAlarmReceiver;
import br.com.twoas.notexrate.threading.MainThreadImpl;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    private MainViewModel mViewModel;
    private ActivityMainBinding mBinding;
    private MainPresenter mPresenter;
    private ForexAlarmReceiver mAlarm;
    private CurrencyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlarm = new ForexAlarmReceiver();
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mPresenter = new MainPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                RestClient.getService(GetConfigDataService.class),
                this,
                mViewModel,
                AppDatabase.getAppDatabase(this).currencyNotifyRepository());

        fillRecycleView();
        mAlarm.updateWidget(this);
        mAlarm.cancelAlarm(this);
        mAlarm.setAlarm(this);
        mAlarm.processQuotes(this);

        mBinding.btnAdd.setOnClickListener(this::onAddClick);

        mPresenter.loadData();
    }

    private void fillRecycleView() {
        RecyclerView recyclerView = mBinding.listCurrencies;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CurrencyAdapter(mViewModel.currencies);
        recyclerView.setAdapter(mAdapter);
    }

    public void onAddClick(View v) {
        Timber.d("Label Clicked");
    }

    @Override
    public void showProgress() {
        mBinding.groupProcess.setVisibility(View.VISIBLE);
        mBinding.groupView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        mBinding.groupProcess.setVisibility(View.INVISIBLE);
        mBinding.groupView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData() {
        mAdapter.updateData(mViewModel.currencies);

    }
}
