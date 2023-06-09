package br.com.twoas.notexrate.domain.interactors.impl;

import androidx.annotation.NonNull;

import java.util.List;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.QuoteInteractor;
import br.com.twoas.notexrate.domain.interactors.base.AbstractInteractor;
import br.com.twoas.notexrate.network.dto.forex.QuoteDTO;
import br.com.twoas.notexrate.network.services.GetForexDataService;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

public class QuoteInteractorImpl extends AbstractInteractor implements QuoteInteractor {

    private final Callback mCallback;
    private final GetForexDataService mService;
    private final String mApiKey;
    private final String mActivityId;
    private final List<String> mIds;

    public QuoteInteractorImpl(Executor threadExecutor,
                               MainThread mainThread,
                               Callback callback,
                               GetForexDataService service,
                               String apiKey,
                               String activityId,
                               List<String> ids) {
        super(threadExecutor, mainThread);
        this.mCallback = callback;
        this.mService = service;
        this.mApiKey = apiKey;
        this.mActivityId = activityId;
        this.mIds = ids;
    }

    @Override
    public void run() {
        try {
            Call<List<QuoteDTO>> call = mService.getQuotes(mApiKey, mActivityId, mIds);
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
        } catch (Exception e) {
            Timber.e(e);
            mCallback.onQuoteFail(e.getMessage());
        }
    }
}
