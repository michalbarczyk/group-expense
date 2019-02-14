package com.michalbarczyk.groupexpense;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<String> names;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public EventViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.item);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventAdapter(List<String> names) {
        this.names = names;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new EventViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
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

