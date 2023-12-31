package br.com.twoas.notexrate.domain.interactors.impl;

import java.util.ArrayList;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.GetAllCurrenciesInteractor;
import br.com.twoas.notexrate.domain.interactors.base.AbstractInteractor;
import br.com.twoas.notexrate.domain.repository.CurrencyNotifyRepository;
import timber.log.Timber;

/**
 * Created by tiSoares on 12/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class GetAllCurrenciesInteractorImpl extends AbstractInteractor implements GetAllCurrenciesInteractor {

    private final Callback mCallback;
    private final CurrencyNotifyRepository mRepository;

    public GetAllCurrenciesInteractorImpl(Executor threadExecutor,
                                          MainThread mainThread,
                                          Callback callback,
                                          CurrencyNotifyRepository repository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
    }

    @Override
    public void run() {
        try {
            mCallback.onGetAllCurrencies(mRepository.getAll());
        } catch (Exception e) {
            Timber.e(e);
            mCallback.onGetAllCurrencies(new ArrayList<>());
        }
    }
}
