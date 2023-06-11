package br.com.twoas.notexrate.domain.interactors.impl;

import androidx.annotation.NonNull;

import java.util.List;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.CurrencyMapInteractor;
import br.com.twoas.notexrate.domain.interactors.base.AbstractForexInteractor;
import br.com.twoas.notexrate.network.dto.forex.IdMapDTO;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import br.com.twoas.notexrate.network.services.GetForexDataService;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by tiSoares on 10/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class CurrencyMapInteractorImpl extends AbstractForexInteractor implements CurrencyMapInteractor {

    private static final Integer NOT_FOUND = 404;
    private final Callback mCallback;
    private final GetForexDataService mService;
    private final List<String> mIds;

    public CurrencyMapInteractorImpl(Executor threadExecutor,
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
            Call<List<IdMapDTO>> call = mService.getCurrenciesCodes(getApiKey(), getActivityId(), mIds);
            call.enqueue(new retrofit2.Callback<>() {
                @Override
                public void onResponse(@NonNull Call<List<IdMapDTO>> call, @NonNull Response<List<IdMapDTO>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<IdMapDTO> codes = response.body();
                        validateReturn(codes);
                    } else {
                        mCallback.onFail("Fail to request quotes");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<IdMapDTO>> call, @NonNull Throwable t) {
                    mCallback.onFail(t.getMessage());
                }
            });
        } catch (Exception e) {
            Timber.e(e);
            mCallback.onFail(e.getMessage());
        }
    }

    private void validateReturn(List<IdMapDTO> codes) {
        if (codes.isEmpty() || NOT_FOUND.equals(codes.get(0).getStatus())) {
            mCallback.onWrongData("Invalid currency!");
        } else {
            mCallback.onSuccess(codes);
        }
    }
}
