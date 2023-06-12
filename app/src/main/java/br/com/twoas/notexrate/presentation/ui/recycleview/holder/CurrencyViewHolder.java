package br.com.twoas.notexrate.presentation.ui.recycleview.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;

import br.com.twoas.notexrate.R;
import br.com.twoas.notexrate.databinding.CurrencyItemBinding;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.utils.Utils;

/**
 * Created by tiSoares on 12/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class CurrencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final View mView;
    private final CurrencyItemBinding mBinder;
    public CurrencyNotify mItemData;

    public CurrencyViewHolder(@NonNull View itemView, CurrencyItemBinding viewBinder) {
        super(itemView);
        mBinder = viewBinder;
        mView = itemView;
        mBinder.currecyCard.setOnClickListener(this);
    }

    public void loadData(CurrencyNotify itemData) {
        mItemData = itemData;
        String[] currencies = mItemData.label.split("/");
        setText(mBinder.price, mItemData.lastPrice);
        setText(mBinder.labelFrom, currencies[0]+" = ");
        setText(mBinder.labelTo, currencies[1]);
        if (BigDecimal.ZERO.compareTo(mItemData.lastPriceChange) >= 0) {
            mBinder.imgIndicator.setImageResource(R.drawable.ic_arrow_drop_down);
        } else {
            mBinder.imgIndicator.setImageResource(R.drawable.ic_arrow_drop_up);
        }
        setText(mBinder.lblUpdatedAt, Utils.formatDate(mItemData.lastUpdate));
        setText(mBinder.priceChange, mItemData.lastPriceChange);
    }

    private void setText(TextView view, String value){
        view.setText(value);
    }

    private void setText(TextView view, BigDecimal decimal) {
        setText(view, Utils.bigDecimalToString(decimal));
    }

    @Override
    public void onClick(View v) {

    }
}
