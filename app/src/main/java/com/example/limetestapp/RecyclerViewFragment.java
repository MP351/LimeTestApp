package com.example.limetestapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewFragment extends Fragment {
    private LinearLayout ll;
    private RecyclerView rv;
    private ValutesAdapter va;
    private ValutesViewModel valutesViewModel;

    private TextView tv_header_name;
    private TextView tv_header_rate;
    private TextView tv_header_code;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null)
            return;

        valutesViewModel = ViewModelProviders.of(getActivity()).get(ValutesViewModel.class);
        va = new ValutesAdapter();
        valutesViewModel.getData().observe(getActivity(),
                valutes ->  va.submitList(valutes));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() == null)
            return null;

        ll = (LinearLayout) inflater.inflate(R.layout.list_layout, container, false);
        rv = ll.findViewById(R.id.rv);
        tv_header_name = ll.findViewById(R.id.header_item_name);
        tv_header_rate = ll.findViewById(R.id.header_item_rate);
        tv_header_code = ll.findViewById(R.id.header_item_code);

        tv_header_name.setOnClickListener(v -> {
            if (valutesViewModel.getSortType() == ValutesViewModel.SortType.NAME_ASC)
                valutesViewModel.setSortType(ValutesViewModel.SortType.NAME_DESC);
            else
                valutesViewModel.setSortType(ValutesViewModel.SortType.NAME_ASC);
        });
        tv_header_code.setOnClickListener(v -> {
            if (valutesViewModel.getSortType() == ValutesViewModel.SortType.CODE_ASC)
                valutesViewModel.setSortType(ValutesViewModel.SortType.CODE_DESC);
            else
                valutesViewModel.setSortType(ValutesViewModel.SortType.CODE_ASC);
        });
        tv_header_rate.setOnClickListener(v -> {
            if (valutesViewModel.getSortType() == ValutesViewModel.SortType.RATE_ASC)
                valutesViewModel.setSortType(ValutesViewModel.SortType.RATE_DESC);
            else
                valutesViewModel.setSortType(ValutesViewModel.SortType.RATE_ASC);
        });

        rv.setAdapter(va);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        return ll;
    }
}
