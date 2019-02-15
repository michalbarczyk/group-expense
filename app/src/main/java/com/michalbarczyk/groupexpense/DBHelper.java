package com.michalbarczyk.groupexpense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "group_expense.db";

    public static final String TABLE_USER = "User";
    public static final String TABLE_EVENT = "Event";
    public static final String TABLE_EXPENSE = "Expense";

    public static final String USER_COL_0 = "UserId";
    public static final String USER_COL_1 = "FirstName";
    public static final String USER_COL_2 = "LastName";

    public static final String EVENT_COL_0 = "EventId";
    public static final String EVENT_COL_1 = "Name";

    public static final String EXPENSE_COL_0 = "ExpenseId";
    public static final String EXPENSE_COL_1 = "LenderId";
    public static final String EXPENSE_COL_2 = "BorrowerId";
    public static final String EXPENSE_COL_3 = "EventId";
    public static final String EXPENSE_COL_4 = "Amount";
    public static final String EXPENSE_COL_5 = "Time";
    public static final String EXPENSE_COL_6 = "Description";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_USER + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
              "FirstName TEXT NOT NULL, LastName TEXT NOT NULL)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_USER);
        onCreate(db);
    }

    public boolean insertUser(String firstname, String lastname) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL_1, firstname);
        contentValues.put(USER_COL_2, lastname);

        return !(db.insert(TABLE_USER, null, contentValues) == -1);
    }

    public boolean insertExpense(int lenderId, int borrowerId, int eventId, int amount, String description) {

        String time = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPENSE_COL_1, lenderId);
        contentValues.put(EXPENSE_COL_2, borrowerId);
        contentValues.put(EXPENSE_COL_3, eventId);
        contentValues.put(EXPENSE_COL_4, amount);
        contentValues.put(EXPENSE_COL_5, time);
        contentValues.put(EXPENSE_COL_6, description);

        return !(db.insert(TABLE_EXPENSE, null, contentValues) == -1);
    }


    public boolean insertEvent(String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EVENT_COL_1, name);

        return !(db.insert(TABLE_EVENT, null, contentValues) == -1);
    }

    public Cursor getAllUsers() {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from User";
        return db.rawQuery(query, null);
    }

    public List<String> getAllEventsNames() {
        List<String> eventList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT Name FROM Event";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            eventList.add(cursor.getString(0));
        }
        // return event list
        return eventList;
    }

    public List<String> getAllUsersNames() {
        List<String> names = new LinkedList<>();

        Cursor cursor = getAllUsers();

        while (cursor.moveToNext()) {
            names.add(cursor.getString(1) + " " + cursor.getString(2));
        }
        // return event list
        return names;
    }

    public int eventResultPerUser(int userId, int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String lendQuery = "select sum(Amount) from " + TABLE_EXPENSE +
                " where " + EXPENSE_COL_1 + " =? " +
                " and " + EXPENSE_COL_3 + " =? ";
        Cursor lendCursor =  db.rawQuery(lendQuery, new String[] {String.valueOf(userId), String.valueOf(eventId)});
        lendCursor.moveToNext();
        int lendValue = Integer.valueOf(lendCursor.getString(0));

        String borrowQuery = "select sum(Amount) from " + TABLE_EXPENSE +
                " where " + EXPENSE_COL_2 + " =? " +
                " and " + EXPENSE_COL_3 + " =? ";
        Cursor borrowCursor =  db.rawQuery(lendQuery, new String[] {String.valueOf(userId), String.valueOf(eventId)});
        borrowCursor.moveToNext();
        int borrowValue = Integer.valueOf(borrowCursor.getString(0));

        return lendValue - borrowValue;
    }

    public Cursor getResultsByEventId(int eventId) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select u.UserId, u.FirstName, u.LastName, " +
                "ifnull((select SUM(Amount) from Expense e where u.UserId = e.LenderId and e.EventId = ?), 0), " +
                "ifnull((select SUM(Amount) from Expense e where u.UserId = e.BorrowerId and e.EventId = ?), 0), " +
                "ifnull((select SUM(Amount) from Expense e where u.UserId = e.LenderId and e.EventId = ?), 0) - " +
                "ifnull((select SUM(Amount) from Expense e where u.UserId = e.BorrowerId and e.EventId = ?), 0) " +
                "from User u where u.UserId in " +
                "(select LenderId from  Expense x where x.EventId = ?) " +
                "or u.UserId in " +
                "(select BorrowerId from  Expense x where x.EventId = ?) ";
        /*String query = "select u.UserId, u.FirstName, u.LastName, " +
                "ifnull(SUM(xL.Amount), 0), " +
                "ifnull(SUM(xB.Amount), 0), " +
                "ifnull(SUM(xL.Amount), 0) - ifnull(SUM(xB.Amount), 0) " +
                "from User u " +
                "left join Expense xL on u.UserId = xL.LenderId " +
                "left join Expense xB on u.UserId = xB.BorrowerId " +
                "where xL.eventId = ? or xB.eventId = ?" +
                "group by u.UserId, u.FirstName, u.LastName"; */

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(eventId),
                String.valueOf(eventId),
                String.valueOf(eventId),
                String.valueOf(eventId),
                String.valueOf(eventId),
                String.valueOf(eventId)});
        return cursor;
    }

    public int getIdFromUserName(String firstname, String lastname) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select " + USER_COL_0 + " from " + TABLE_USER +
                " where " + USER_COL_1 + "=?" +
                " and " + USER_COL_2 + "=?";
        Cursor cursor = db.rawQuery(query, new String[] {firstname, lastname});

        cursor.moveToNext();
        return Integer.valueOf(cursor.getString(0));
    }

    public int getIdFromEventName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select " + EVENT_COL_0 + " from " + TABLE_EVENT +
                " where " + EVENT_COL_1 + "=?";
        Cursor cursor = db.rawQuery(query, new String[] {name});
        cursor.moveToNext();
        return Integer.valueOf(cursor.getString(0));
    }
}
