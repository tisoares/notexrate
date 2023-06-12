package br.com.twoas.notexrate.domain.interactors;

import java.util.List;

import br.com.twoas.notexrate.domain.interactors.base.Interactor;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;

/**
 * Created by tiSoares on 12/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public interface GetAllCurrenciesInteractor extends Interactor {
    interface Callback {
        void onGetAllCurrencies(List<CurrencyNotify> currencies);
    }
}
