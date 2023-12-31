package br.com.twoas.notexrate.presentation.presenters.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.twoas.notexrate.Constants;
import br.com.twoas.notexrate.database.AppDatabase;
import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.CurrencyMapInteractor;
import br.com.twoas.notexrate.domain.interactors.GetChartDataInteractor;
import br.com.twoas.notexrate.domain.interactors.GetCurrencyByIdInteractor;
import br.com.twoas.notexrate.domain.interactors.GetCurrencyByWdgIdInteractor;
import br.com.twoas.notexrate.domain.interactors.QuoteInteractor;
import br.com.twoas.notexrate.domain.interactors.SaveCurrencyInteractor;
import br.com.twoas.notexrate.domain.interactors.impl.CurrencyMapInteractorImpl;
import br.com.twoas.notexrate.domain.interactors.impl.GetChartDataInteractorImpl;
import br.com.twoas.notexrate.domain.interactors.impl.GetCurrencyByIdInteractorImpl;
import br.com.twoas.notexrate.domain.interactors.impl.GetCurrencyByWdgIdInteractorImpl;
import br.com.twoas.notexrate.domain.interactors.impl.QuoteInteractorImpl;
import br.com.twoas.notexrate.domain.interactors.impl.SaveCurrencyInteractorImpl;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.network.dto.forex.ChartDataDTO;
import br.com.twoas.notexrate.network.dto.forex.ChartPeriod;
import br.com.twoas.notexrate.network.dto.forex.IdMapDTO;
import br.com.twoas.notexrate.network.dto.forex.QuoteDTO;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import br.com.twoas.notexrate.network.services.GetForexDataService;
import br.com.twoas.notexrate.presentation.presenters.CurrencyDetailActivityPresenter;
import br.com.twoas.notexrate.presentation.presenters.base.AbstractForexPresenter;
import br.com.twoas.notexrate.presentation.ui.viewmodel.CurrencyViewModel;

/**
 * Created by tiSoares on 10/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class CurrencyDetailActivityPresenterImpl extends AbstractForexPresenter implements CurrencyDetailActivityPresenter,
        GetCurrencyByWdgIdInteractor.Callback, CurrencyMapInteractor.Callback, QuoteInteractor.Callback,
        GetCurrencyByIdInteractor.Callback, GetChartDataInteractor.Callback{

    private final CurrencyViewModel mViewModel;
    private final View mView;
    private final AppDatabase mDataBase;
    private final GetForexDataService mServiceForex;

    public CurrencyDetailActivityPresenterImpl(Executor executor,
                                               MainThread mainThread,
                                               GetConfigDataService configService,
                                               View View,
                                               CurrencyViewModel ViewModel,
                                               AppDatabase appDatabase,
                                               GetForexDataService serviceForex) {
        super(executor, mainThread, configService);
        this.mViewModel = ViewModel;
        this.mView = View;
        this.mDataBase = appDatabase;
        this.mServiceForex = serviceForex;
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
    public void setWidgetId(String widgetId) {
        try {
            mViewModel.widgetId = Integer.valueOf(widgetId);
        } catch (Exception e) {
            mViewModel.widgetId = Constants.DEFAULT_WDG;
        }
        if (Constants.DEFAULT_WDG.equals(mViewModel.widgetId)) {
            mView.showError("Invalid WidgetId");
        } else {
            findCurrencyByWgdId(mViewModel.widgetId);
        }
    }

    @Override
    public void setCurrencyId(String id) {
        try {
            mViewModel.currencyId = Integer.valueOf(id);
        } catch (Exception e) {
            mViewModel.currencyId = Constants.DEFAULT_WDG;
        }
        if (Constants.DEFAULT_WDG.equals(mViewModel.currencyId)) {
            this.onSuccess((CurrencyNotify) null);
        } else {
            findCurrencyByCurrencyId(mViewModel.currencyId);
        }
    }

    @Override
    public void saveCurrencyNotify(CurrencyNotify currencyNotify) {
        SaveCurrencyInteractor interactor = new SaveCurrencyInteractorImpl(
                mExecutor,
                mMainThread,
                (CurrencyNotify... currencyNotifies) -> {
                    mView.hideProgress();
                    mView.saved(Arrays
                            .stream(currencyNotifies)
                            .collect(Collectors.toList()));
                },
                mDataBase.currencyNotifyRepository(),
                currencyNotify
        );
        interactor.execute();
    }

    @Override
    public void saveCurrencyNotify(String code, String from, String to, BigDecimal min, BigDecimal max) {
        CurrencyNotify currencyNotify;
        if (mViewModel.currencyNotify == null ) {
            currencyNotify = new CurrencyNotify();
            currencyNotify.uid = Constants.DEFAULT_WDG.equals(mViewModel.currencyId) ? null : mViewModel.currencyId;
            currencyNotify.wdgId = Constants.DEFAULT_WDG.equals(mViewModel.widgetId) ? null : mViewModel.widgetId;
        } else {
            currencyNotify = mViewModel.currencyNotify;
        }
        currencyNotify.code = code;
        currencyNotify.label = from+"/"+to;
        currencyNotify.minValueAlert = min;
        currencyNotify.maxValueAlert = max;
        currencyNotify.intervalUpdate = Constants.MILLI_INTERVAL;
        currencyNotify.lastPrice = BigDecimal.ZERO;
        currencyNotify.lastPriceChange = BigDecimal.ZERO;
        currencyNotify.lastUpdate = new Date();
        saveCurrencyNotify(currencyNotify);
    }

    @Override
    public void getCurrencyCode(String from, String to) {
        mView.showProgress();
        CurrencyMapInteractor interactor = new CurrencyMapInteractorImpl(
                mExecutor,
                mMainThread,
                mConfigService,
                this,
                mServiceForex,
                Collections.singletonList(from+to));
        interactor.execute();
    }

    @Override
    public void refreshQuote() {
        mView.showProgress();
        new QuoteInteractorImpl(mExecutor,
                mMainThread,
                mConfigService,
                this,
                mServiceForex,
                Collections.singletonList(mViewModel.currencyNotify.code))
                .execute();
        refreshChartData();
    }

    private void refreshChartData() {
        new GetChartDataInteractorImpl(mExecutor,
                mMainThread,
                mConfigService,
                this,
                mServiceForex,
                ChartPeriod.DAY,
                mViewModel.currencyNotify.code).execute();
    }

    @Override
    public void onError(String message) {
        mView.showError(message);
    }

    private void findCurrencyByWgdId(int id) {
        mView.showProgress();
        GetCurrencyByWdgIdInteractor interactor = new GetCurrencyByWdgIdInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mDataBase.currencyNotifyRepository(),
                id);
        interactor.execute();
    }

    private void findCurrencyByCurrencyId(Integer currencyId) {
        mView.showProgress();
        GetCurrencyByIdInteractor interactor = new GetCurrencyByIdInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mDataBase.currencyNotifyRepository(),
                currencyId);
        interactor.execute();
    }

    @Override
    public void onSuccess(CurrencyNotify currencyNotify) {
        mView.hideProgress();
        mViewModel.currencyNotify = currencyNotify;
        if (currencyNotify == null) {
            mView.openSettingsFragment();
        } else {
            mView.openDetailsFragment();
        }
    }

    @Override
    public void onSuccess(List<IdMapDTO> idMaps) {
        mView.hideProgress();
        if (!idMaps.isEmpty()) {
            mView.currencyCode(idMaps.get(0).getExternalCode());
        } else {
           mView.wrongCurrency();
        }
    }

    @Override
    public void onFail(String message) {
        mView.hideProgress();
        mView.showError(message);
    }

    @Override
    public void onWrongData(String message) {
        mView.hideProgress();
        mView.wrongCurrency();
    }

    @Override
    public void onQuoteSuccess(List<QuoteDTO> quotes) {
        mView.hideProgress();
        if (!quotes.isEmpty()) {
            mViewModel.quote = quotes.get(0);
            mView.loadQuote();
        }
    }

    @Override
    public void onQuoteFail(String error) {
        mView.hideProgress();
        mView.showError(error);
    }

    @Override
    public void onGetByIdSuccess(CurrencyNotify currency) {
        mView.hideProgress();
        mViewModel.currencyNotify = currency;
        if (currency == null) {
            mView.openSettingsFragment();
        } else {
            mView.openDetailsFragment();
        }
    }

    @Override
    public void onSuccess(ChartDataDTO chartData) {
        mView.hideProgress();
        mView.loadChart(chartData);
    }

    @Override
    public void onFailure(String error) {
        mView.hideProgress();
        mView.showError(error);
    }
}
