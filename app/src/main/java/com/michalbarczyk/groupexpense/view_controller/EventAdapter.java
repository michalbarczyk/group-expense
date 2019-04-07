package com.michalbarczyk.groupexpense.view_controller;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

import com.michalbarczyk.groupexpense.R;
import com.michalbarczyk.groupexpense.model.DBHelper;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<String> names;

    static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        LinearLayout linearLayoutCardView;


        EventViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.item);
            linearLayoutCardView = v.findViewById(R.id.linear_layout_card_view);
        }
    }

    EventAdapter(List<String> names) {
        this.names = names;
    }

    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new EventViewHolder(v);
    }

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
            builder.append(MoneyConverter.getRealMoneyValue(
                    Integer.valueOf(
                            cursor.getString(3))));
            builder.append("\n");
            builder.append("borrowed = ");
            builder.append(MoneyConverter.getRealMoneyValue(
                    Integer.valueOf(
                            cursor.getString(4))));
            builder.append("\n");
            builder.append("to be given = ");
            builder.append(MoneyConverter.getRealMoneyValue(
                    Integer.valueOf(
                            cursor.getString(5))));
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

