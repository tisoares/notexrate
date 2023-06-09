package br.com.twoas.notexrate.domain.interactors.impl;

import androidx.annotation.NonNull;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.MSNConfigInteractor;
import br.com.twoas.notexrate.domain.interactors.base.AbstractInteractor;
import br.com.twoas.notexrate.network.dto.config.MSNConfigDTO;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import retrofit2.Call;
import retrofit2.Response;

public class MSNConfigInteractorImpl extends AbstractInteractor implements MSNConfigInteractor {

    private final Callback mCallback;
    private final GetConfigDataService mService;

    public MSNConfigInteractorImpl(Executor threadExecutor,
                                   MainThread mainThread,
                                   Callback callback,
                                   GetConfigDataService service) {
        super(threadExecutor, mainThread);
        this.mCallback = callback;
        this.mService = service;
    }

    @Override
    public void run() {
        Call<MSNConfigDTO> call = mService.getConfig();
        call.enqueue(new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MSNConfigDTO> call, @NonNull Response<MSNConfigDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MSNConfigDTO result = response.body();
                    mCallback.onMSNConfigSuccess(result.
                            getConfigs().
                            getHoroscopeDefault().
                            getProperty().
                            getHoroscopeAnswerServiceClientSettings());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MSNConfigDTO> call, @NonNull Throwable t) {
                mCallback.onMSNConfigFail(t.getMessage());
            }
        });
    }
}
