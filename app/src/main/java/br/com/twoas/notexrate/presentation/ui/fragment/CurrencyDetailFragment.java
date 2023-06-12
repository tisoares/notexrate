package br.com.twoas.notexrate.presentation.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import br.com.twoas.notexrate.R;
import br.com.twoas.notexrate.databinding.FragmentCurrencyDetailBinding;
import br.com.twoas.notexrate.network.dto.forex.QuoteDTO;
import br.com.twoas.notexrate.presentation.ui.viewmodel.CurrencyViewModel;
import br.com.twoas.notexrate.utils.Utils;


public class CurrencyDetailFragment extends Fragment {

    public interface Listener {
        void onRefresh();

        void onEdit();
    }

    private CurrencyViewModel mViewModel;
    private FragmentCurrencyDetailBinding mBinding;
    private Listener mListener;

    public static CurrencyDetailFragment newInstance(Listener listener) {
        CurrencyDetailFragment fragment = new CurrencyDetailFragment();
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CurrencyViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentCurrencyDetailBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnRefresh.setOnClickListener(v -> onRefresh());
        mBinding.imgEdit.setOnClickListener(v -> onEdit());
        mListener.onRefresh();
    }

    @SuppressLint("SetTextI18n")
    public void loadData() {
        QuoteDTO quote = mViewModel.quote;
        if (quote != null) {
            mBinding.labelFrom.setText(quote.getFromCurrency() + " = ");
            mBinding.labelTo.setText(quote.getCurrency());
            mBinding.price.setText(Utils.bigDecimalToString(quote.getPrice()));
            if (BigDecimal.ZERO.compareTo(quote.getPriceChange()) >= 0) {
                mBinding.imgIndicator.setImageResource(R.drawable.ic_arrow_drop_down);
                mBinding.priceChange.setTextColor(ContextCompat.getColor(requireContext(),R.color.green));
                mBinding.lblPercentage.setTextColor(ContextCompat.getColor(requireContext(),R.color.green));
            } else {
                mBinding.imgIndicator.setImageResource(R.drawable.ic_arrow_drop_up);
                mBinding.priceChange.setTextColor(ContextCompat.getColor(requireContext(),R.color.red));
                mBinding.lblPercentage.setTextColor(ContextCompat.getColor(requireContext(),R.color.red));
            }
            mBinding.lblUpdatedAt.setText(Utils.formatDate(quote.getTimeLastUpdated()));
            mBinding.priceChange.setText(Utils.bigDecimalToString(quote.getPriceChange()));
            mBinding.lblPercentage.setText("("+Utils.bigDecimalToString(quote.getPriceChangePercent()
                    .setScale(2, RoundingMode.HALF_UP)) + "%)");
            mBinding.priceChange2.setText(Utils.bigDecimalToString(quote.getPriceChange()));
            mBinding.lblPriceChangePercent.setText(Utils.bigDecimalToString(quote.getPriceChangePercent()));
            mBinding.lastClose.setText(Utils.bigDecimalToString(quote.getPricePreviousClose()));
            mBinding.lastOpen.setText(Utils.bigDecimalToString(quote.getPriceDayOpen()));
            mBinding.minPrice.setText(Utils.bigDecimalToString(quote.getPriceDayLow()));
            mBinding.maxPrice.setText(Utils.bigDecimalToString(quote.getPriceDayHigh()));
            mBinding.lblAsk.setText(Utils.bigDecimalToString(quote.getPriceAsk()));
            mBinding.lblBid.setText(Utils.bigDecimalToString(quote.getPriceBid()));
            mBinding.lblLastClose.setText(Utils.formatDate(quote.getDatePreviousClose()));
            mBinding.lblLastUpdate.setText(Utils.formatDate(quote.getTimeLastUpdated()));
            if (mViewModel.currencyNotify != null) {
                mBinding.lblMinAlarm.setText(Utils.bigDecimalToString(mViewModel.currencyNotify.minValueAlert));
                mBinding.lblMaxAlarm.setText(Utils.bigDecimalToString(mViewModel.currencyNotify.maxValueAlert));
            }
        }
    }

    private void onEdit() {
        mListener.onEdit();
    }

    private void onRefresh() {
        mListener.onRefresh();
    }
}