package com.michalbarczyk.groupexpense.view_controller;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MoneyTextWatcher implements TextWatcher {

    private EditText editText;

    public MoneyTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        try
        {
            editText.removeTextChangedListener(this);
            String value = editText.getText().toString();

            if (value != null && !value.equals("")) {

                String str = editText.getText().toString().replaceAll("[.]", "");
                if (!value.equals(""))
                    editText.setText(getMoneyFormattedString(str));
                editText.setSelection(editText.getText().toString().length());
            }
            editText.addTextChangedListener(this);
            return;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            editText.addTextChangedListener(this);
        }

    }

    public static String getMoneyFormattedString(String value)
    {
        StringBuilder initialZerosReducer = new StringBuilder(); // it is to store values without 1...n initial zeros

        int j = 0;
        while (String.valueOf(value.charAt(j)).equals("0"))
            j++;

        for (; j < value.length(); j++) {
            initialZerosReducer.append(String.valueOf(value.charAt(j)));
        }

        String reducedValue = initialZerosReducer.toString();

        StringBuilder moneyBuilder = new StringBuilder();

        if (value.length() == 1) {

            moneyBuilder.append("0.0");
            moneyBuilder.append(reducedValue);

        } else if (value.length() == 2) {

            moneyBuilder.append("0.");
            moneyBuilder.append(reducedValue);

        } else {

            for (int i = 0; i < reducedValue.length() - 2; i++) {

                String token = String.valueOf(reducedValue.charAt(i));
                moneyBuilder.append(token);
            }

            moneyBuilder.append(".");
            moneyBuilder.append(reducedValue.charAt(reducedValue.length() - 2));
            moneyBuilder.append(reducedValue.charAt(reducedValue.length() - 1));
        }

        return moneyBuilder.toString();
    }
}