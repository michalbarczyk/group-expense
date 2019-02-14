package com.michalbarczyk.groupexpense;


import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    DBHelper dbHelper;
    Button btnAddEvent;
    EditText editEventName, input;


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

        RecyclerView recyclerView = (RecyclerView)getView().findViewById(R.id.recycler_view_event);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        UserAdapter adapter = new UserAdapter(dbHelper.getAllEventsList());

        recyclerView.setAdapter(adapter);

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("TITLE");
        builder.setMessage("messagex222");

        input = new EditText(getContext());
        builder.setView(input);

        builder.setPositiveButton("submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String txt = input.getText().toString();
                Toast.makeText(getContext(), txt, Toast.LENGTH_LONG).show();
            }
        });

        final AlertDialog alertDialog = builder.create();

        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab_add_event);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "WORKS", Toast.LENGTH_LONG).show();
                alertDialog.show();
            }
        });
    }
}
