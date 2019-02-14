package com.michalbarczyk.groupexpense;


import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    DBHelper dbHelper;
    EditText inputFirstname, inputLastname;

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

        RecyclerView recyclerView = (RecyclerView)getView().findViewById(R.id.recycler_view_user);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        UserAdapter adapter = new UserAdapter(dbHelper.getAllUsersList());

        recyclerView.setAdapter(adapter);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

    // Add a TextView here for the "Title" label, as noted in the comments
        EditText titleBox = new EditText(getContext());
        titleBox.setHint("title");
        layout.addView(titleBox); // Notice this is an add method

    // Add another TextView here for the "Description" label
        EditText descriptionBox = new EditText(getContext());
        descriptionBox.setHint("Description");
        layout.addView(descriptionBox); // Another add method

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setView(layout);

        builder.setPositiveButton("submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String txt = inputFirstname.getText().toString();
                Toast.makeText(getContext(), txt, Toast.LENGTH_LONG).show();
            }
        });

        final android.app.AlertDialog alertDialog = builder.create();

        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab_add_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "WORKS", Toast.LENGTH_LONG).show();
                alertDialog.show();
            }
        });
    }

    /*private void enableAddUser() {
        btnAddUser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dbHelper.insertUser(editFirstname.getText().toString(), editLastname.getText().toString()))
                            Toast.makeText(getActivity(), "Data inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(), "Data not inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void enableShowUsers() {
        btnShowUsers.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = dbHelper.getAllUsers();

                        if (res.getCount() == 0) {
                            showMessage("Error", "Nothing found");
                            return;
                        }

                        StringBuilder builder = new StringBuilder();
                        while (res.moveToNext()) {
                            builder.append("UserId: " + res.getString(0)+"\n");
                            builder.append("FirstName: " + res.getString(1)+"\n");
                            builder.append("LastName: " + res.getString(2)+"\n"+"\n");
                        }

                        showMessage("ALL USERS IN DB", builder.toString());
                    }
                }
        );
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    */
}
