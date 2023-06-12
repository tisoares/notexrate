package br.com.twoas.notexrate.presentation.ui.recycleview.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.twoas.notexrate.databinding.CurrencyItemBinding;
import br.com.twoas.notexrate.domain.model.CurrencyNotify;
import br.com.twoas.notexrate.presentation.ui.recycleview.holder.CurrencyViewHolder;

/**
 * Created by tiSoares on 12/06/2023.
 * Author: Tiago Soares
 * Email: tisoares@outlook.com
 */
public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyViewHolder>{

    private List<CurrencyNotify> mData;

    public CurrencyAdapter(List<CurrencyNotify> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CurrencyItemBinding binder = CurrencyItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CurrencyViewHolder(binder.getRoot(), binder);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        holder.loadData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updateData(List<CurrencyNotify> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
