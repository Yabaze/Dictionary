package com.thomas.mirakle.dictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;

    DatabaseAccess(Context context) {
        this.openHelper = new DatabaseHelper(context);
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }


    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public List<String> getAllWords() {
        open();
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT "+DatabaseHelper.TABLE_COLUMN1+" FROM "+DatabaseHelper.TABLE_NAME+" ORDER BY 'ASC'", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return list;
    }

    public List<String> getCurrentWords(String a) {
        open();
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT "+DatabaseHelper.TABLE_COLUMN1+" FROM "+DatabaseHelper.TABLE_NAME+" where "+DatabaseHelper.TABLE_COLUMN1+" LIKE '"+a+"%'"+" ORDER BY 'ASC'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return list;
    }

    public List<Dict> getWordDetail(String s) {
        open();
        List<Dict> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT "+DatabaseHelper.TABLE_COLUMN1+","+DatabaseHelper.TABLE_COLUMN2+","+DatabaseHelper.TABLE_COLUMN3+" FROM "+DatabaseHelper.TABLE_NAME+" where "+DatabaseHelper.TABLE_COLUMN1+" = '"+s+"'"+" ORDER BY 'ASC'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new Dict(cursor.getString(0),cursor.getString(1),cursor.getString(2)));

            cursor.moveToNext();
        }
        cursor.close();
        close();
        return list;
    }

    public List<String> getSingleWords(String query) {
        open();
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT "+DatabaseHelper.TABLE_COLUMN1+" FROM "+DatabaseHelper.TABLE_NAME+" where "+DatabaseHelper.TABLE_COLUMN1+"='"+query+"' ORDER BY 'ASC'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return list;
    }
}