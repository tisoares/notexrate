package br.com.twoas.notexrate.presentation.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import br.com.twoas.notexrate.Constants;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.network.dto.forex.QuoteDTO;

public class CurrencyViewModel extends ViewModel {

    public Integer widgetId = Constants.DEFAULT_WDG;

    public Integer currencyId = Constants.DEFAULT_WDG;
    public CurrencyNotify currencyNotify = null;

    public QuoteDTO quote = null;

}