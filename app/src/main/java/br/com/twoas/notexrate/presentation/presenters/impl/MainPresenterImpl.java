package br.com.twoas.notexrate.presentation.presenters.impl;

import android.widget.Toast;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.MSNConfigInteractor;
import br.com.twoas.notexrate.domain.interactors.SampleInteractor;
import br.com.twoas.notexrate.domain.interactors.impl.MSNConfigInteractorImpl;
import br.com.twoas.notexrate.network.RestClient;
import br.com.twoas.notexrate.network.dto.config.HoroscopeSettings;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import br.com.twoas.notexrate.presentation.presenters.MainPresenter;
import br.com.twoas.notexrate.presentation.presenters.base.AbstractPresenter;
import timber.log.Timber;

/**
 * Created by dmilicic on 12/13/15.
 */
public class MainPresenterImpl extends AbstractPresenter implements MainPresenter,
        MSNConfigInteractor.Callback {

    private MainPresenter.View mView;

    public MainPresenterImpl(Executor executor,
                             MainThread mainThread,
                             View view) {
        super(executor, mainThread);
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
        MSNConfigInteractor interactor = new MSNConfigInteractorImpl(mExecutor,
                mMainThread,
                this,
                RestClient.getConfigService(GetConfigDataService.class));
        interactor.execute();
    }

    @Override
    public void onMSNConfigSuccess(HoroscopeSettings settings) {
        Timber.d("APIKEY: "+settings.getApikey());
    }

    @Override
    public void onMSNConfigFail(String error) {
        Timber.e(error);
    }
}
