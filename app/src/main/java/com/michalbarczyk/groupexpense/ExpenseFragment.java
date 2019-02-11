package com.michalbarczyk.groupexpense;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {

    Spinner eventSpinner, lenderSpinner, borrowerSpinner;
    Button btnProceed;
    EditText editAmount, editDescription;
    DBHelper dbHelper;

    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expense, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        dbHelper = new DBHelper(getContext());

        btnProceed = (Button) getView().findViewById(R.id.btn_proceed);

        eventSpinner = (Spinner) getView().findViewById(R.id.event_spinner);
        lenderSpinner = (Spinner) getView().findViewById(R.id.lender_spinner);
        borrowerSpinner = (Spinner) getView().findViewById(R.id.borrower_spinner);

        editAmount = (EditText) getView().findViewById(R.id.amount);
        editDescription = (EditText) getView().findViewById(R.id.description);

        fillEventSpinner();
        fillLenderSpinner();
        fillBorrowerSpinner();

        enableProceed();
    }

    private void fillEventSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dbHelper.getAllEventsList());
        eventSpinner.setAdapter(adapter);
    }

    private void fillLenderSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dbHelper.getAllUsersList());
        lenderSpinner.setAdapter(adapter);
    }

    private void fillBorrowerSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dbHelper.getAllUsersList());
        borrowerSpinner.setAdapter(adapter);
    }

    private void enableProceed() {
        btnProceed.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int lenderId = (int)lenderSpinner.getSelectedItem();
                        int borrowerId = (int)borrowerSpinner.getSelectedItem();
                        int eventId = (int)eventSpinner.getSelectedItem();
                        int amount = moneyToInt(editAmount.getText().toString());
                        String desc = (String)editDescription.getText().toString();

                        if (dbHelper.insertExpense(lenderId, borrowerId, eventId, amount, desc))
                            Toast.makeText(getActivity(), "Data inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(), "Data not inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private int moneyToInt(String money) {
        String[] splited = money.split(".");
        int convertedMoney = Integer.valueOf(splited[0]) * 100 + Integer.valueOf(splited[1]);

        return convertedMoney;
    }

}
