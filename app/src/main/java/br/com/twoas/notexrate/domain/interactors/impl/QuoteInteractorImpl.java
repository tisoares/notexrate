package br.com.twoas.notexrate.domain.interactors.impl;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.QuoteInteractor;
import br.com.twoas.notexrate.domain.interactors.base.AbstractForexInteractor;
import br.com.twoas.notexrate.network.dto.forex.QuoteDTO;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import br.com.twoas.notexrate.network.services.GetForexDataService;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

public class QuoteInteractorImpl extends AbstractForexInteractor implements QuoteInteractor {

    private final Callback mCallback;
    private final GetForexDataService mService;
    private final List<String> mIds;

    public QuoteInteractorImpl(Executor threadExecutor,
                               MainThread mainThread,
                               GetConfigDataService configService,
                               Callback callback,
                               GetForexDataService service,
                               List<String> ids) {
        super(threadExecutor, mainThread, configService);
        this.mCallback = callback;
        this.mService = service;
        this.mIds = ids;
    }

    @Override
    public void run() {
        try {
            if (mIds.size() == 1) {
                processOneQuote(mIds.get(0));
            } else {
                processMultiQuotes(mIds);
            }
        } catch (Exception e) {
            Timber.e(e);
            mCallback.onQuoteFail(e.getMessage());
        }
    }

    private void processOneQuote(String code) {
        Call<List<QuoteDTO>> call = mService.getQuotes(getApiKey(), getActivityId(), code);
        call.enqueue(new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<QuoteDTO>> call, @NonNull Response<List<QuoteDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<QuoteDTO> quotes = response.body();
                    mCallback.onQuoteSuccess(quotes);
                } else {
                    mCallback.onQuoteFail("Fail to request quotes");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<QuoteDTO>> call, @NonNull Throwable t) {
                mCallback.onQuoteFail(t.getMessage());
            }
        });
    }

    private void processMultiQuotes(List<String> mIds) {

        Call<List<List<QuoteDTO>>> call = mService.getQuotesMulti(getApiKey(), getActivityId(), mIds);
        call.enqueue(new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<List<QuoteDTO>>> call, @NonNull Response<List<List<QuoteDTO>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mCallback.onQuoteSuccess(reduceQuotes(response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<List<QuoteDTO>>> call, @NonNull Throwable t) {
                mCallback.onQuoteFail(t.getMessage());
            }
        });
    }
    private List<QuoteDTO> reduceQuotes(List<List<QuoteDTO>> multiQuote) {
        List<QuoteDTO> result = new ArrayList<>();
        for (List<QuoteDTO> quotes: multiQuote) {
            result.add(quotes.get(0));
        }
        return result;
    }
}
