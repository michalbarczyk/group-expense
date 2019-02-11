package com.michalbarczyk.groupexpense;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    DBHelper dbHelper;
    Button btnAddEvent;
    EditText editEventName;


    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        dbHelper = new DBHelper(getContext());

        editEventName = (EditText) getView().findViewById(R.id.edit_event_name);
        btnAddEvent = (Button) getView().findViewById(R.id.btn_add_event);

        enableAddEvent();
    }

    private void enableAddEvent() {
        btnAddEvent.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dbHelper.insertEvent(editEventName.getText().toString()))
                            Toast.makeText(getActivity(), "Data inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(), "Data not inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

}
