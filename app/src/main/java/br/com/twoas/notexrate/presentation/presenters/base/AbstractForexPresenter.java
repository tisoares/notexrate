package br.com.twoas.notexrate.presentation.presenters.base;

import br.com.twoas.notexrate.domain.executor.Executor;
import br.com.twoas.notexrate.domain.executor.MainThread;
import br.com.twoas.notexrate.network.services.GetConfigDataService;

/**
 * Created by tiSoares on 10/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class AbstractForexPresenter extends AbstractPresenter {

    protected final GetConfigDataService mConfigService;

    public AbstractForexPresenter(Executor executor, MainThread mainThread, GetConfigDataService configService) {
        super(executor, mainThread);
        this.mConfigService = configService;
    }
}
