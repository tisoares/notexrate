package br.com.twoas.notexrate.presentation.presenters.impl;

import java.util.List;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.GetAllCurrenciesInteractor;
import br.com.twoas.notexrate.domain.interactors.impl.GetAllCurrenciesInteractorImpl;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.domain.repository.CurrencyNotifyRepository;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import br.com.twoas.notexrate.presentation.presenters.MainPresenter;
import br.com.twoas.notexrate.presentation.presenters.base.AbstractForexPresenter;
import br.com.twoas.notexrate.presentation.ui.viewmodel.MainViewModel;


public class MainPresenterImpl extends AbstractForexPresenter implements MainPresenter, GetAllCurrenciesInteractor.Callback {

    private MainPresenter.View mView;
    private final MainViewModel mViewModel;
    private final CurrencyNotifyRepository mRepository;

    public MainPresenterImpl(Executor executor,
                             MainThread mainThread,
                             GetConfigDataService configService,
                             View view,
                             MainViewModel viewModel,
                             CurrencyNotifyRepository repository) {
        super(executor, mainThread, configService);
        mView = view;
        mViewModel = viewModel;
        mRepository = repository;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void loadData() {
        mView.showProgress();
        new GetAllCurrenciesInteractorImpl(mExecutor,
                mMainThread,
                this,
                mRepository).execute();
    }

    @Override
    public void onGetAllCurrencies(List<CurrencyNotify> currencies) {
        mView.hideProgress();
        mViewModel.currencies = currencies;
        mView.showData();
    }
}
