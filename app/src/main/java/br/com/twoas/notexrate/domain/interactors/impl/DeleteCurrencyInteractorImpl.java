package br.com.twoas.notexrate.domain.interactors.impl;

import android.telecom.Call;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.DeleteCurrencyInteractor;
import br.com.twoas.notexrate.domain.interactors.base.AbstractInteractor;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.domain.repository.CurrencyNotifyRepository;

/**
 * Created by tiSoares on 12/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class DeleteCurrencyInteractorImpl extends AbstractInteractor implements DeleteCurrencyInteractor {

    private final Callback mCallback;
    private final CurrencyNotifyRepository mRepository;
    private final CurrencyNotify mCurrencyNotify;

    public DeleteCurrencyInteractorImpl(Executor threadExecutor,
                                        MainThread mainThread,
                                        Callback callback,
                                        CurrencyNotifyRepository repository,
                                        CurrencyNotify currencyNotify) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
        mCurrencyNotify = currencyNotify;
    }

    @Override
    public void run() {
        mRepository.delete(mCurrencyNotify);
        mCallback.onDeleted();
    }
}
