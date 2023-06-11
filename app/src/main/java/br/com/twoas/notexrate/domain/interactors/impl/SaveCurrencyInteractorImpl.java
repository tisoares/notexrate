package br.com.twoas.notexrate.domain.interactors.impl;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.SaveCurrencyInteractor;
import br.com.twoas.notexrate.domain.interactors.base.AbstractInteractor;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.domain.repository.CurrencyNotifyRepository;

/**
 * Created by tiSoares on 10/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class SaveCurrencyInteractorImpl extends AbstractInteractor implements SaveCurrencyInteractor {

    private final Callback mCallback;
    private final CurrencyNotifyRepository mRepository;
    private final CurrencyNotify[] mCurrencyNotifies;

    public SaveCurrencyInteractorImpl(Executor threadExecutor,
                                      MainThread mainThread,
                                      Callback callback,
                                      CurrencyNotifyRepository repository,
                                      CurrencyNotify... currencyNotifies) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
        mCurrencyNotifies = currencyNotifies;
    }

    @Override
    public void run() {
        mRepository.insertAll(mCurrencyNotifies);
        mCallback.onSaved(mCurrencyNotifies);
    }
}
