package br.com.twoas.notexrate.domain.interactors;

import br.com.twoas.notexrate.domain.interactors.base.Interactor;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;

/**
 * Created by tiSoares on 10/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public interface GetCurrencyByWdgIdInteractor extends Interactor {
    interface Callback {
        void onSuccess(CurrencyNotify currencyNotify);
    }
}
