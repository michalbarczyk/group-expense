package com.michalbarczyk.groupexpense.view_controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import com.michalbarczyk.groupexpense.R;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<String> names;

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ExpenseViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.item);
        }
    }

    public ExpenseAdapter(List<String> names) {
        this.names = names;
    }

    @Override
    public ExpenseAdapter.ExpenseViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ExpenseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {

        holder.textView.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}