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
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    DBHelper dbHelper;
    EditText inputName;
    EventAdapter adapter;
    RecyclerView recyclerView;
    LinearLayout popLayout;
    FloatingActionButton fab;

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        dbHelper = new DBHelper(getContext());

        prepareInputs();
        preparePopLayout();

        recyclerView = (RecyclerView)getView().findViewById(R.id.recycler_view_event);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new EventAdapter(dbHelper.getAllEventsNames());
        recyclerView.setAdapter(adapter);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setView(popLayout);

        builder.setPositiveButton("submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (dbHelper.insertEvent(inputName.getText().toString()))
                    Toast.makeText(getActivity(), "New event added", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(), "New event not added", Toast.LENGTH_LONG).show();
            }
        });

        final android.app.AlertDialog alertDialog = builder.create();

        fab = (FloatingActionButton) getView().findViewById(R.id.fab_add_event);
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
        popLayout.addView(inputName);
    }

    private void prepareInputs() {
        inputName = new EditText(getContext());
        inputName.setHint("event's name");

    }
}
