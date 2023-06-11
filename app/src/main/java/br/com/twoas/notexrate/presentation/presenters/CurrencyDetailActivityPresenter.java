package br.com.twoas.notexrate.presentation.presenters;

import java.math.BigDecimal;
import java.util.List;

import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.presentation.presenters.base.BasePresenter;
import br.com.twoas.notexrate.presentation.ui.BaseView;

/**
 * Created by tiSoares on 10/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public interface CurrencyDetailActivityPresenter extends BasePresenter {

    interface View extends BaseView {
        void openSettingsFragment();
        void openDetailsFragment();
        void currencyCode(String code);
        void wrongCurrency();
        void saved(List<CurrencyNotify> currencyNotifies);
        void loadQuote();
    }

    void setWidgetId(String widgetId);
    void saveCurrencyNotify(CurrencyNotify currencyNotify);
    void saveCurrencyNotify(String code, String from, String to, BigDecimal min, BigDecimal max);
    void getCurrencyCode(String from, String to);
    void refreshQuote();

}
