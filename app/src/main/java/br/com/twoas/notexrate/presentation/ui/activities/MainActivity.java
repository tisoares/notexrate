package br.com.twoas.notexrate.presentation.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import br.com.twoas.notexrate.databinding.ActivityMainBinding;
import br.com.twoas.notexrate.domain.executor.impl.ThreadExecutor;
import br.com.twoas.notexrate.presentation.presenters.MainPresenter;
import br.com.twoas.notexrate.presentation.presenters.impl.MainPresenterImpl;
import br.com.twoas.notexrate.threading.MainThreadImpl;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    private static final String  DATA_IDENTIFIER = "WDG_DATA_IDENTIFIER";

    private ActivityMainBinding binding;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mPresenter = new MainPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), this);
        mPresenter.getConfig();

        if (getIntent().getExtras()!= null &&  getIntent().getExtras().containsKey(DATA_IDENTIFIER)){
            binding.label.setText(getIntent().getExtras().getString(DATA_IDENTIFIER));
        }
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
