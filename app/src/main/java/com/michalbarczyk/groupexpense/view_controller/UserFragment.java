package com.michalbarczyk.groupexpense.view_controller;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.michalbarczyk.groupexpense.R;
import com.michalbarczyk.groupexpense.model.DBHelper;

public class UserFragment extends Fragment {

    DBHelper dbHelper;
    EditText inputFirstname, inputLastname;
    UserAdapter adapter;
    RecyclerView recyclerView;
    LinearLayout popLayout;
    FloatingActionButton fab;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        dbHelper = new DBHelper(getContext());

        prepareInputs();
        preparePopLayout();

        recyclerView = (RecyclerView)getView().findViewById(R.id.recycler_view_user);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserAdapter(dbHelper.getAllUsersNames());
        recyclerView.setAdapter(adapter);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setView(popLayout);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (dbHelper.insertUser(inputFirstname.getText().toString().trim(), inputLastname.getText().toString().trim()))
                    Toast.makeText(getActivity(), "New user added", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(), "New user not added", Toast.LENGTH_LONG).show();

            }
        });

        final android.app.AlertDialog alertDialog = builder.create();

        fab = (FloatingActionButton) getView().findViewById(R.id.fab_add_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });
    }

    private void preparePopLayout() {
        popLayout = new LinearLayout(getContext());
        popLayout.setOrientation(LinearLayout.VERTICAL);
        popLayout.addView(inputFirstname);
        popLayout.addView(inputLastname);
    }

    private void prepareInputs() {
        inputFirstname = new EditText(getContext());
        inputLastname = new EditText(getContext());
        inputFirstname.setHint("First name");
        inputLastname.setHint("Last name");
    }
}
