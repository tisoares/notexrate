package br.com.twoas.notexrate.domain.interactors.impl;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.GetCurrencyByWdgIdInteractor;
import br.com.twoas.notexrate.domain.interactors.base.AbstractInteractor;
import br.com.twoas.notexrate.domain.repository.CurrencyNotifyRepository;

/**
 * Created by tiSoares on 10/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class GetCurrencyByWdgIdInteractorImpl extends AbstractInteractor implements GetCurrencyByWdgIdInteractor {

    private final CurrencyNotifyRepository mRepository;
    private final int mWdgId;
    private final Callback mCallback;

    public GetCurrencyByWdgIdInteractorImpl(Executor threadExecutor,
                                            MainThread mainThread,
                                            Callback callback,
                                            CurrencyNotifyRepository repository,
                                            int wdgId) {
        super(threadExecutor, mainThread);
        this.mRepository = repository;
        this.mWdgId = wdgId;
        this.mCallback = callback;
    }

    @Override
    public void run() {
       mCallback.onSuccess(mRepository.findByWdgId(mWdgId));
    }
}
