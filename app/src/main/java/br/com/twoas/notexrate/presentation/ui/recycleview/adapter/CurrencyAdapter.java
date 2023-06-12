package br.com.twoas.notexrate.presentation.ui.recycleview.adapter;

import android.view.LayoutInflater;
import android.view.View;
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
    public interface ItemClickListener {
        void onItemClick(View view, CurrencyNotify item);
    }
    private List<CurrencyNotify> mData;
    private final ItemClickListener mClickListener;

    public CurrencyAdapter(List<CurrencyNotify> mData, ItemClickListener clickListener) {
        this.mData = mData;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CurrencyItemBinding binder = CurrencyItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CurrencyViewHolder(binder.getRoot(), binder);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        holder.loadData(mData.get(position), mClickListener);
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
