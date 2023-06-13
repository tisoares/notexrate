package br.com.twoas.notexrate.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.twoas.notexrate.Constants;
import br.com.twoas.notexrate.database.AppDatabase;
import br.com.twoas.notexrate.databinding.ActivityMainBinding;
import br.com.twoas.notexrate.domain.executor.impl.ThreadExecutor;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.network.RestClient;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import br.com.twoas.notexrate.helper.ForexHelper;
import br.com.twoas.notexrate.presentation.presenters.MainPresenter;
import br.com.twoas.notexrate.presentation.presenters.impl.MainPresenterImpl;
import br.com.twoas.notexrate.presentation.ui.recycleview.adapter.CurrencyAdapter;
import br.com.twoas.notexrate.presentation.ui.viewmodel.MainViewModel;
import br.com.twoas.notexrate.threading.MainThreadImpl;

public class MainActivity extends AppCompatActivity implements MainPresenter.View, CurrencyAdapter.ItemClickListener {

    private MainViewModel mViewModel;
    private ActivityMainBinding mBinding;
    private MainPresenter mPresenter;
    private ForexHelper mForex;
    private CurrencyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mForex = ForexHelper.getInstance();
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
        mForex.updateWidget(this);
        mForex.cancelAlarm(this);
        mForex.setAlarm(this);
        mForex.processQuotes(this);

        mBinding.btnAdd.setOnClickListener(this::onAddClick);

        mPresenter.loadData();
    }


    private void fillRecycleView() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getLayoutPosition();
                if (mPresenter.allowDelete(mViewModel.currencies.get(position))) {
                    showConfirmDelete(mViewModel.currencies.get(position));
                } else {
                    Toast.makeText(MainActivity.this, "It is linked to a Widget, delete the Widget first.", Toast.LENGTH_SHORT).show();
                    showData();
                }
            }
        };
        RecyclerView recyclerView = mBinding.listCurrencies;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CurrencyAdapter(mViewModel.currencies, this);
        recyclerView.setAdapter(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void showConfirmDelete(CurrencyNotify currencyNotify) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Currency");
        builder.setMessage(String.format("Confirm deletion of %s?", currencyNotify.label));
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
            mPresenter.deleteCurrency(currencyNotify);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
            showData();
        }).create().show();
    }

    public void onAddClick(View v) {
        openCurrencyDetail(-1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadData();
    }

    @Override
    public void showProgress() {
        mBinding.groupProcess.setVisibility(View.VISIBLE);
        mBinding.groupView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        this.runOnUiThread(() -> {
            mBinding.groupProcess.setVisibility(View.INVISIBLE);
            mBinding.groupView.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData() {
        this.runOnUiThread(() -> mAdapter.updateData(mViewModel.currencies));
    }

    @Override
    public void onDeleted() {
        mPresenter.loadData();
        mForex.processQuotes(this);
    }

    @Override
    public void onItemClick(View view, CurrencyNotify item) {
        openCurrencyDetail(item.uid);
    }

    private void openCurrencyDetail(int id) {
        Intent intent = new Intent(this, CurrencyDetailActivity.class);
        intent.putExtra(Constants.CURRENCY_IDENTIFIER, ""+id);
        startActivity(intent);
        mPresenter.loadData();
    }

}
