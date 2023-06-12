package br.com.twoas.notexrate.presentation.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import br.com.twoas.notexrate.domain.model.CurrencyNotify;

/**
 * Created by tiSoares on 12/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class MainViewModel extends ViewModel {

    public List<CurrencyNotify> currencies = new ArrayList<>();

}
