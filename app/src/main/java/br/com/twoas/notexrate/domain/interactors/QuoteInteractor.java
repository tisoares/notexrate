package br.com.twoas.notexrate.domain.interactors;

import java.util.List;

import br.com.twoas.notexrate.domain.interactors.base.Interactor;
import br.com.twoas.notexrate.network.dto.forex.QuoteDTO;

public interface QuoteInteractor extends Interactor {

    interface Callback {
        void onQuoteSuccess(List<QuoteDTO> quotes);
        void onQuoteFail(String error);
    }
}
