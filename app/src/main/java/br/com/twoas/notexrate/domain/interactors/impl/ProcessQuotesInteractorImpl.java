package br.com.twoas.notexrate.domain.interactors.impl;

import java.util.List;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.ProcessQuotesInteractor;
import br.com.twoas.notexrate.domain.interactors.QuoteInteractor;
import br.com.twoas.notexrate.domain.interactors.base.AbstractForexInteractor;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.domain.repository.CurrencyNotifyRepository;
import br.com.twoas.notexrate.network.dto.forex.QuoteDTO;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import br.com.twoas.notexrate.network.services.GetForexDataService;
import timber.log.Timber;

/**
 * Created by tiSoares on 10/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class ProcessQuotesInteractorImpl extends AbstractForexInteractor implements ProcessQuotesInteractor {

    private final Callback mCallback;
    private final CurrencyNotifyRepository mRepository;
    private final GetForexDataService mService;

    public ProcessQuotesInteractorImpl(Executor threadExecutor,
                                       MainThread mainThread,
                                       GetConfigDataService configService,
                                       Callback callback,
                                       CurrencyNotifyRepository repository,
                                       GetForexDataService service) {
        super(threadExecutor, mainThread, configService);
        this.mCallback = callback;
        this.mRepository = repository;
        this.mService = service;
    }

    @Override
    public void run() {
        List<String> codes = getCurrencyCodes();
        if (!codes.isEmpty()) {
            new QuoteInteractorImpl(mThreadExecutor,
                    mMainThread,
                    mConfigService,
                    new QuoteInteractor.Callback() {
                        @Override
                        public void onQuoteSuccess(List<QuoteDTO> quotes) {
                            new Thread(() -> {
                                updateCurrencies(quotes);
                                mCallback.onSuccess(mRepository.getAll());
                            }).start();
                        }

                        @Override
                        public void onQuoteFail(String error) {
                            Timber.e(error);
                        }
                    },
                    mService,
                    codes).execute();
        }
    }
    private List<String> getCurrencyCodes() {
        return mRepository.getAllCodes();
    }

    private void updateCurrencies(List<QuoteDTO> quotes) {
        for (QuoteDTO quote : quotes) {
            updateCurrency(quote);
        }
    }

    private void updateCurrency(QuoteDTO quote) {
        List<CurrencyNotify> currencies = mRepository.findByCode(quote.getInstrumentId());
        for (CurrencyNotify currency: currencies) {
            currency.lastUpdate = quote.getTimeLastUpdated();
            currency.lastPrice = quote.getPrice();
            currency.lastPriceChange = quote.getPriceChange();
            mRepository.insertAll(currency);
        }
    }

}
