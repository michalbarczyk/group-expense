package com.michalbarczyk.groupexpense;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;

public class AddButtonHandler {

    private FloatingActionButton fab;
    private int itemId;
    private Context context;

    public AddButtonHandler(FloatingActionButton fab, int itemId, Context context) {
        this.fab = fab;
        this.itemId = itemId;
        this.context = context;
    }

    public void handle() {
        handleAddUser();
    }

    private void handleAddUser() {

        EditText input = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Look at this dialog!")
                .setCancelable(true)
                .setView(input)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
