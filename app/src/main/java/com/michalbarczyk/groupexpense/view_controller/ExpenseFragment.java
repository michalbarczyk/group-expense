package com.michalbarczyk.groupexpense.view_controller;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.michalbarczyk.groupexpense.R;
import com.michalbarczyk.groupexpense.model.DBHelper;

//TODO on-click popup window containing expense description

public class ExpenseFragment extends Fragment {

    DBHelper dbHelper;
    Spinner lenderSpinner, borrowerSpinner, eventSpinner;
    EditText inputAmount, inputDescription;
    ExpenseAdapter adapter;
    RecyclerView recyclerView;
    LinearLayout popLayout;
    FloatingActionButton fab;

    public ExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_expense, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        dbHelper = new DBHelper(getContext());

        prepareSpinners();
        prepareInputs();
        preparePopLayout();

        recyclerView = (RecyclerView)getView().findViewById(R.id.recycler_view_expense);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ExpenseAdapter(this.getExpensesReports());
        recyclerView.setAdapter(adapter);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setView(popLayout);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String lenderName = lenderSpinner.getSelectedItem().toString();
                String[] splitLenderName = lenderName.split(" ");
                int lenderId = dbHelper.getIdFromUserName(splitLenderName[0], splitLenderName[1]);

                String borrowerName = borrowerSpinner.getSelectedItem().toString();
                String[] splitBorrowerName = borrowerName.split(" ");
                int borrowerId = dbHelper.getIdFromUserName(splitBorrowerName[0], splitBorrowerName[1]);

                String eventName = eventSpinner.getSelectedItem().toString();
                int eventId = dbHelper.getIdFromEventName(eventName);

                int amount = MoneyConverter.valueOf(inputAmount.getText().toString());
                String desc = inputDescription.getText().toString();

                if (lenderId != borrowerId && dbHelper.insertExpense(lenderId, borrowerId, eventId, amount, desc.trim()))
                    Toast.makeText(getActivity(), "Data inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(), "Data not inserted", Toast.LENGTH_LONG).show();
            }
        });

        final android.app.AlertDialog alertDialog = builder.create();

        fab = (FloatingActionButton) getView().findViewById(R.id.fab_add_expense);
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
        popLayout.addView(lenderSpinner);
        popLayout.addView(borrowerSpinner);
        popLayout.addView(eventSpinner);
        popLayout.addView(inputAmount);
        popLayout.addView(inputDescription);
    }

    private void prepareSpinners() {

        lenderSpinner = new Spinner(getContext());
        borrowerSpinner = new Spinner(getContext());
        eventSpinner = new Spinner(getContext());

        fillLenderSpinner();
        fillBorrowerSpinner();
        fillEventSpinner();
    }

    private void fillEventSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, dbHelper.getAllEventsNames());
        eventSpinner.setAdapter(adapter);
    }

    private void fillLenderSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, dbHelper.getAllUsersNames());
        lenderSpinner.setAdapter(adapter);
    }

    private void fillBorrowerSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, dbHelper.getAllUsersNames());
        borrowerSpinner.setAdapter(adapter);
    }

    private void prepareInputs() {

        inputAmount = new EditText(getContext());
        inputAmount.addTextChangedListener(new MoneyTextWatcher(inputAmount));
        inputAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputAmount.setGravity(Gravity.RIGHT);
        inputDescription = new EditText(getContext());
        inputAmount.setHint("Amount");
        inputDescription.setHint("Description");
    }

    private List<String> getExpensesReports() {

        List<String> raports = new LinkedList<>();

        Cursor cursor = dbHelper.getAllExpensesDetails();

        while (cursor.moveToNext()) {

            StringBuilder builder = new StringBuilder();
            builder.append(cursor.getString(5));
            builder.append(" | ");
            builder.append(cursor.getString(6).replaceAll("_", " | "));
            builder.append("\n");
            builder.append(cursor.getString(0));
            builder.append(" ");
            builder.append(cursor.getString(1));
            builder.append(" -> ");
            builder.append(cursor.getString(2));
            builder.append(" ");
            builder.append(cursor.getString(3));
            builder.append(":\nAmount = ");
            builder.append(MoneyConverter.getRealMoneyValue(
                    Integer.valueOf(
                            cursor.getString(4))));

            raports.add(builder.toString());
        }

        Collections.reverse(raports);

        return raports;


    }

}
