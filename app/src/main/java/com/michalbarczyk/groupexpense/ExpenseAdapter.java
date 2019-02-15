package com.michalbarczyk.groupexpense;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    private List<String> names;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ExpenseViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.item);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExpenseAdapter(List<String> names) {
        this.names = names;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExpenseAdapter.ExpenseViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ExpenseViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(names.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return names.size();
    }
}