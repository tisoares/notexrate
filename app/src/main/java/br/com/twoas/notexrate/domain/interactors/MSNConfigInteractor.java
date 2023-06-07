package br.com.twoas.notexrate.domain.interactors;

import br.com.twoas.notexrate.domain.interactors.base.Interactor;
import br.com.twoas.notexrate.network.dto.config.HoroscopeSettings;

public interface MSNConfigInteractor extends Interactor {
    interface Callback {
        void onMSNConfigSuccess(HoroscopeSettings settings);
        void onMSNConfigFail(String error);
    }
}
