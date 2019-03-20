package com.michalbarczyk.groupexpense;

import android.util.Log;

public class MoneyHandler {

    public static int valueOf(String moneyStr) {

        String delim = "[.]";



        moneyStr = moneyStr.trim();

        String[] parsedMoneyStr = moneyStr.split(delim);

        if (parsedMoneyStr.length != 2) {
            //TODO exeption
        }
        Log.i("->[0]", parsedMoneyStr[0]);
        Log.i("->[1]", parsedMoneyStr[1]);
        int fullMoney = Integer.valueOf(parsedMoneyStr[0]);
        int partMoney = Integer.valueOf(parsedMoneyStr[1]);

        return  fullMoney * 100 + partMoney;
    }
}
