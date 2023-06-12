package br.com.twoas.notexrate.domain.interactors.impl;

import java.util.List;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.DeleteWidgetRemovedInteractor;
import br.com.twoas.notexrate.domain.interactors.base.AbstractInteractor;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.domain.repository.CurrencyNotifyRepository;

/**
 * Created by tiSoares on 12/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class DeleteWidgetRemovedInteractorImpl extends AbstractInteractor implements DeleteWidgetRemovedInteractor {

    private final Callback mCallback;
    private final CurrencyNotifyRepository mRepository;
    private final int[] mWdgIds;

    public DeleteWidgetRemovedInteractorImpl(Executor threadExecutor,
                                             MainThread mainThread,
                                             Callback callback,
                                             CurrencyNotifyRepository repository,
                                             int[] wdgIds) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRepository = repository;
        mWdgIds = wdgIds;
    }

    @Override
    public void run() {
        List<CurrencyNotify> currencies = mRepository.findAllByDeletedWdgIds(mWdgIds);
        for (CurrencyNotify currency : currencies) {
            mRepository.delete(currency);
        }
        mCallback.onDeleteSuccess();
    }
}
