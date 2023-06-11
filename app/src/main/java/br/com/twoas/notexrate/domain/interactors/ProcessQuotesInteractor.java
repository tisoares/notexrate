package br.com.twoas.notexrate.domain.interactors;

import java.util.List;

import br.com.twoas.notexrate.domain.interactors.base.Interactor;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;

/**
 * Created by tiSoares on 10/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public interface ProcessQuotesInteractor extends Interactor {
    interface Callback {
        void onSuccess(List<CurrencyNotify> currencyNotifies);
    }
}
