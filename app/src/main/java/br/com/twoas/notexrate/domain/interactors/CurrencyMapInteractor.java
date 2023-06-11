package br.com.twoas.notexrate.domain.interactors;

import java.util.List;

import br.com.twoas.notexrate.domain.interactors.base.Interactor;
import br.com.twoas.notexrate.network.dto.forex.IdMapDTO;

/**
 * Created by tiSoares on 10/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public interface CurrencyMapInteractor extends Interactor {
    interface Callback {
        void onSuccess(List<IdMapDTO> idMaps);
        void onFail(String message);
        void onWrongData(String message);
    }
}
