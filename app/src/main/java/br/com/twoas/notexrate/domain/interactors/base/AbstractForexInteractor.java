package br.com.twoas.notexrate.domain.interactors.base;

import br.com.twoas.notexrate.Constants;
import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.network.dto.config.MSNConfigDTO;
import br.com.twoas.notexrate.network.services.GetConfigDataService;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by tiSoares on 10/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public abstract class AbstractForexInteractor extends AbstractInteractor {

    protected final GetConfigDataService mConfigService;

    public AbstractForexInteractor(Executor threadExecutor, MainThread mainThread, GetConfigDataService configService) {
        super(threadExecutor, mainThread);
        this.mConfigService = configService;
    }

    private static String apiKey = null;

    protected String getApiKey() {
        if (apiKey == null) {
            try {
                Call<MSNConfigDTO> call = mConfigService.getConfig();
                Response<MSNConfigDTO> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    apiKey = response.body()
                            .getConfigs()
                            .getHoroscopeDefault()
                            .getProperty()
                            .getHoroscopeAnswerServiceClientSettings()
                            .getApikey();
                }
            } catch (Exception e) {
                return null;
            }
        }
        return apiKey;
    }

    protected String getActivityId() {
        return Constants.ACTIVITY_ID;
    }

    @Override
    public void execute() {

        // mark this interactor as running
        this.mIsRunning = true;

        // start running this interactor in a background thread
        mThreadExecutor.execute(this);
    }
}
