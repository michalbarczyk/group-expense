package com.michalbarczyk.groupexpense;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    DBHelper dbHelper;
    EditText editFirstname, editLastname;
    Button btnAddUser, btnShowUsers;

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

        editFirstname = (EditText) getView().findViewById(R.id.edit_firstname);
        editLastname = (EditText) getView().findViewById(R.id.edit_lastname);
        btnAddUser = (Button) getView().findViewById(R.id.btn_add_user);
        btnShowUsers = (Button) getView().findViewById(R.id.btn_show_users);

        enableAddUser();
        enableShowUsers();
    }

    private void enableAddUser() {
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

}
