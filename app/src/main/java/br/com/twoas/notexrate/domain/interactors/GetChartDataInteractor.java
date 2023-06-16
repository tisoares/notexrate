package br.com.twoas.notexrate.domain.interactors;

import br.com.twoas.notexrate.domain.interactors.base.Interactor;
import br.com.twoas.notexrate.network.dto.forex.ChartDataDTO;

/**
 * Created by tiSoares on 16/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public interface GetChartDataInteractor extends Interactor {

    interface Callback {
        void onSuccess(ChartDataDTO chartData);
        void onFailure(String error);
    }
}
