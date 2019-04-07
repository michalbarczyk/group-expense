package com.michalbarczyk.groupexpense.view_controller;

import android.util.Log;

import static java.lang.Math.abs;

class MoneyConverter {

    static int valueOf(String moneyStr) {

        String delim = "[.]";

        moneyStr = moneyStr.trim();

        String[] parsedMoneyStr = moneyStr.split(delim);

        if (parsedMoneyStr.length != 2) {
            throw new IllegalArgumentException("zero or more than one \".\" in parsed money input");
        }
        Log.i("->[0]", parsedMoneyStr[0]);
        Log.i("->[1]", parsedMoneyStr[1]);
        int fullMoney = Integer.valueOf(parsedMoneyStr[0]);
        int partMoney = Integer.valueOf(parsedMoneyStr[1]);

        return fullMoney * 100 + partMoney;
    }

    static String getRealMoneyValue(int money) {

        StringBuilder builder = new StringBuilder();

        if (money < 0)
            builder.append("-");

        int fullMoney = abs(money) / 100;
        int partMoney = abs(money) % 100;

        builder.append(String.valueOf(fullMoney));
        builder.append(".");
        if (partMoney < 10)
            builder.append("0");
        builder.append(String.valueOf(partMoney));

        return builder.toString();
    }
}
