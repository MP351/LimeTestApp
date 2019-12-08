package com.example.limetestapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.limetestapp.data.pojo.Valute;

public class ValutesAdapter extends ListAdapter<Valute, ValutesAdapter.ValuteViewHolder> {
    public ValutesAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ValuteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ValuteViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ValuteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ValuteViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_rate;
        TextView tv_code;

        public ValuteViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.item_name);
            tv_rate = itemView.findViewById(R.id.item_rate);
            tv_code = itemView.findViewById(R.id.item_code);
        }

        public void bind(Valute item) {
            tv_name.setText(item.getName());
            tv_rate.setText(String.format("%.2f", item.getCurrentValue()));
            tv_code.setText(item.getCharCode());
        }
    }

    private static final DiffUtil.ItemCallback<Valute> DIFF_CALLBACK
            = new DiffUtil.ItemCallback<Valute>() {
        @Override
        public boolean areItemsTheSame(@NonNull Valute oldItem, @NonNull Valute newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Valute oldItem, @NonNull Valute newItem) {
            return oldItem.equals(newItem);
        }
    };
}
