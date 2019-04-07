package com.michalbarczyk.groupexpense.view_controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import com.michalbarczyk.groupexpense.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<String> names;

    static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        UserViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.item);
        }
    }

    UserAdapter(List<String> names) {
        this.names = names;
    }

    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        holder.mTextView.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
