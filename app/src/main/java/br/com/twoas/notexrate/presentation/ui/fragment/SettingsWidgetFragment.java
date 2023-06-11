package br.com.twoas.notexrate.presentation.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.util.Objects;

import br.com.twoas.notexrate.databinding.FragmentSettingsWidgetBinding;
import br.com.twoas.notexrate.presentation.ui.viewmodel.CurrencyViewModel;
import br.com.twoas.notexrate.utils.Utils;

public class SettingsWidgetFragment extends Fragment {

    public interface Listener {
        void onSave(String code, String from, String to, BigDecimal min, BigDecimal max);
        void onValidateCurrency(String from, String to);
    }

    private FragmentSettingsWidgetBinding mBinding;
    private CurrencyViewModel mModel;
    private  Listener mListener;

    public SettingsWidgetFragment() {
        // Required empty public constructor
    }

    public static SettingsWidgetFragment newInstance(Listener listener) {
        SettingsWidgetFragment fragment = new SettingsWidgetFragment();
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mModel = new ViewModelProvider(requireActivity()).get(CurrencyViewModel.class);
        mBinding = FragmentSettingsWidgetBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnSave.setOnClickListener(v -> validateCurrency());
        mBinding.lblWdgId.setText(mModel.widgetId.toString());
        mBinding.lblWdgId.setVisibility(View.INVISIBLE);
        loadData();
    }

    private void loadData() {
        if (mModel.currencyNotify != null) {
            mBinding.txtMinValue.getEditText().setText(Utils.bigDecimalToString(mModel.currencyNotify.minValueAlert));
            mBinding.txtMaxValue.getEditText().setText(Utils.bigDecimalToString(mModel.currencyNotify.maxValueAlert));
            String[] currencies = mModel.currencyNotify.label.split("/");
            mBinding.txtFrom.getEditText().setText(currencies[0]);
            mBinding.txtTo.getEditText().setText(currencies[1]);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding= null;
    }

    private void validateCurrency() {
        String from = Objects.requireNonNull(mBinding.txtFrom.getEditText()).getText().toString();
        String to = Objects.requireNonNull(mBinding.txtTo.getEditText()).getText().toString();
        mListener.onValidateCurrency(from, to);
    }

    public void save(String code) {
        String from = Objects.requireNonNull(mBinding.txtFrom.getEditText()).getText().toString();
        String to = Objects.requireNonNull(mBinding.txtTo.getEditText()).getText().toString();
        BigDecimal min = tryConvertBigDecimal(mBinding.txtMinValue, Objects.requireNonNull(mBinding.txtMinValue.getEditText()).getText().toString());
        BigDecimal max = tryConvertBigDecimal(mBinding.txtMaxValue, Objects.requireNonNull(mBinding.txtMaxValue.getEditText()).getText().toString());
        if (min == null || max == null) {
            return;
        }

        mListener.onSave(code, from, to, min, max);
    }

    public void wrongCurrencies() {
        showInputError(mBinding.txtFrom);
        showInputError(mBinding.txtTo);
    }
    private BigDecimal tryConvertBigDecimal(TextInputLayout txt, String value){
        if (value == null || "".equals(value)){
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(value);
        } catch (Exception e) {
           showInputError(txt);
           return null;
        }
    }

    private void showInputError(TextInputLayout txt) {
        txt.setError("Invalid value");
        txt.setErrorEnabled(true);
    }
}