package br.com.twoas.notexrate.presentation.presenters.impl;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import br.com.twoas.notexrate.presentation.presenters.MainPresenter;
import br.com.twoas.notexrate.presentation.presenters.base.AbstractForexPresenter;

/**
 * Created by dmilicic on 12/13/15.
 */
public class MainPresenterImpl extends AbstractForexPresenter implements MainPresenter {

    private MainPresenter.View mView;

    public MainPresenterImpl(Executor executor,
                             MainThread mainThread,
                             GetConfigDataService configService,
                             View view) {
        super(executor, mainThread, configService);
        mView = view;
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
    public void getConfig() {

    }

}
