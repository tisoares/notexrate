package br.com.twoas.notexrate.domain.interactors;

import br.com.twoas.notexrate.domain.interactors.base.Interactor;

/**
 * Created by tiSoares on 12/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public interface DeleteCurrencyInteractor extends Interactor {
    interface Callback {
        void onDeleted();
    }
}
