package br.com.twoas.notexrate.presentation.presenters;

import br.com.twoas.notexrate.presentation.presenters.base.BasePresenter;
import br.com.twoas.notexrate.presentation.ui.BaseView;


public interface MainPresenter extends BasePresenter {

    interface View extends BaseView {
        void showData();
    }
    void loadData();
}
