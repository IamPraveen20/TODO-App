package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DPhelper extends SQLiteOpenHelper {

    private static final String DB_name = "Tester";
    private static final String DB_table = "Task";
    private static final String DB_column = "Taskname";

    public DPhelper(Context context) {
        super(context, DB_name, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String query = String.format(" CREATE TABLE " + DB_table + " (" + DB_column + "ID INTEGER PRIMARY KEY AUTOINCREMENT,   TEXT NOT NULL);" );
       db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      String query = String.format(" DELETE TABLE IF EXISTS %s ",DB_table);
      db.execSQL(query);
      onCreate(db);

    }

    public void insertNewTask(String task){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_column,task);
        db.insertWithOnConflict(DB_table,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

    }

    public void deleteTask(String task){
        SQLiteDatabase dp = getWritableDatabase();
        dp.delete(DB_table,DB_column  +" = ? ", new String[]{task});
        dp.close();

    }

    public ArrayList<String> getTaskList(){

        ArrayList<String> tasklist = new ArrayList<>();
        SQLiteDatabase dp = getWritableDatabase();
        Cursor cursor = dp.query(DB_table,new String[]{DB_column},null,null,null,null,null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_column);
            tasklist.add(cursor.getString(index));
        }
        cursor.close();
        dp.close();

       return tasklist;
    }

}
