package com.michalbarczyk.groupexpense;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        public LinearLayout linearLayoutCardView;


        public EventViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.item);
            linearLayoutCardView = v.findViewById(R.id.linear_layout_card_view);
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

        final String eventName = names.get(position);

        holder.mTextView.setText(names.get(position));
        holder.linearLayoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage(v, eventName);

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return names.size();
    }

    private void showMessage(View v, String eventName) {

        DBHelper dbHelper = new DBHelper(v.getContext());

        StringBuilder builder = new StringBuilder();
        int eventId = dbHelper.getIdFromEventName(eventName);
        Cursor cursor = dbHelper.getResultsByEventId(eventId);

        while(cursor.moveToNext()) {
            builder.append(cursor.getString(1));
            builder.append(" ");
            builder.append(cursor.getString(2));
            builder.append("\n");
            builder.append("lent = ");
            builder.append(cursor.getString(3));
            builder.append("\n");
            builder.append("borrowed = ");
            builder.append(cursor.getString(4));
            builder.append("\n");
            builder.append("to be given = ");
            builder.append(cursor.getString(5));
            builder.append("\n");
            builder.append("\n");
        }

        AlertDialog.Builder result = new AlertDialog.Builder(v.getContext());
        result.setCancelable(true);
        result.setTitle("Summary of " + eventName);
        result.setMessage(builder.toString());
        result.show();
    }
}

