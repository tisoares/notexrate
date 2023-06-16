package br.com.twoas.notexrate.domain.interactors.impl;

import androidx.annotation.NonNull;

import java.util.List;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.GetChartDataInteractor;
import br.com.twoas.notexrate.domain.interactors.base.AbstractForexInteractor;
import br.com.twoas.notexrate.network.dto.forex.ChartDataDTO;
import br.com.twoas.notexrate.network.dto.forex.ChartPeriod;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import br.com.twoas.notexrate.network.services.GetForexDataService;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by tiSoares on 16/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class GetChartDataInteractorImpl extends AbstractForexInteractor implements GetChartDataInteractor {

    private final Callback mCallback;
    private final GetForexDataService mService;
    private final ChartPeriod mPeriod;
    private final String mId;

    public GetChartDataInteractorImpl(Executor threadExecutor,
                                      MainThread mainThread,
                                      GetConfigDataService configService,
                                      Callback callback,
                                      GetForexDataService service,
                                      ChartPeriod period,
                                      String id) {
        super(threadExecutor, mainThread, configService);
        mCallback = callback;
        mService = service;
        mPeriod = period;
        mId = id;
    }

    @Override
    public void run() {
        try {
            Call<List<ChartDataDTO>> call = mService.getChartData(getApiKey(),
                    getActivityId(), mPeriod.getPeriodToRequest(), mId);
            call.enqueue(new retrofit2.Callback<>() {
                @Override
                public void onResponse(@NonNull Call<List<ChartDataDTO>> call, @NonNull Response<List<ChartDataDTO>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<ChartDataDTO> chartLst = response.body();
                        mCallback.onSuccess(chartLst.isEmpty() ? null : chartLst.get(0));
                    } else {
                        mCallback.onFailure("Fail to request quotes");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<ChartDataDTO>> call, @NonNull Throwable t) {
                    mCallback.onFailure(t.getMessage());
                }
            });
        } catch (Exception e) {
            Timber.e(e);
            mCallback.onFailure(e.getMessage());
        }
    }
}
