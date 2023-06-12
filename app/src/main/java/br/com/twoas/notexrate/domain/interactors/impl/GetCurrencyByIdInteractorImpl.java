package br.com.twoas.notexrate.domain.interactors.impl;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.GetCurrencyByIdInteractor;
import br.com.twoas.notexrate.domain.interactors.base.AbstractInteractor;
import br.com.twoas.notexrate.domain.repository.CurrencyNotifyRepository;

/**
 * Created by tiSoares on 12/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class GetCurrencyByIdInteractorImpl extends AbstractInteractor implements GetCurrencyByIdInteractor {
    private final Callback mCallback;
    private final CurrencyNotifyRepository mRepository;
    private final int mId;

    public GetCurrencyByIdInteractorImpl(Executor threadExecutor,
                                         MainThread mainThread,
                                         Callback callback,
                                         CurrencyNotifyRepository repository,
                                         int id) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
        mId = id;
    }

    @Override
    public void run() {
        mCallback.onGetByIdSuccess(mRepository.findById(mId));
    }
}
