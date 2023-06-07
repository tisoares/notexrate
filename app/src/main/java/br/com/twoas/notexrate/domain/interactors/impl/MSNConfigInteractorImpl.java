package br.com.twoas.notexrate.domain.interactors.impl;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.domain.interactors.MSNConfigInteractor;
import br.com.twoas.notexrate.domain.interactors.base.AbstractInteractor;
import br.com.twoas.notexrate.network.dto.config.MSNConfigDTO;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import retrofit2.Call;
import retrofit2.Response;

public class MSNConfigInteractorImpl extends AbstractInteractor implements MSNConfigInteractor {

    private static final String EXP_TYPE = "AppConfig";
    private static final String EXP_INSTANCE = "default";
    private static final String APP_TYPE = "finance";
    private static final String VERSION = "20230602.223";
    private static final String TARGET_SCOPE = "{\"audienceMode\":\"none\",\"browser\":{\"browserType\":\"chrome\",\"version\":\"113\",\"ismobile\":\"true\"},\"deviceFormFactor\":\"mobile\",\"domain\":\"www.msn.com\",\"locale\":{\"content\":{\"language\":\"pt\",\"market\":\"br\"},\"display\":{\"language\":\"pt\",\"market\":\"br\"}},\"os\":\"android\",\"platform\":\"web\",\"pageType\":\"finance::portfolio\",\"pageExperiments\":[]}";

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
        Call<MSNConfigDTO> call = mService.getConfig(EXP_TYPE, EXP_INSTANCE, APP_TYPE, VERSION, TARGET_SCOPE);
        call.enqueue(new retrofit2.Callback<MSNConfigDTO>() {
            @Override
            public void onResponse(Call<MSNConfigDTO> call, Response<MSNConfigDTO> response) {
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
            public void onFailure(Call<MSNConfigDTO> call, Throwable t) {
                mCallback.onMSNConfigFail(t.getMessage());
            }
        });
    }
}
