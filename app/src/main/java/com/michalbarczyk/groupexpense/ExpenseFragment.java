package com.michalbarczyk.groupexpense;


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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
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
        adapter = new ExpenseAdapter(dbHelper.getAllExpensesSummaries());
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

                int amount = Integer.valueOf(inputAmount.getText().toString());
                String desc = inputDescription.getText().toString();

                if (lenderId != borrowerId && dbHelper.insertExpense(lenderId, borrowerId, eventId, amount, desc))
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
        inputDescription = new EditText(getContext());
        inputAmount.setHint("Amount");
        inputDescription.setHint("Description");
    }

}
