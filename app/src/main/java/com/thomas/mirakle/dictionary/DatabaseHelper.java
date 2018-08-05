package com.thomas.mirakle.dictionary;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Dictionary.db";
    public static final String TABLE_NAME = "entries";
    public static final String TABLE_COLUMN1 = "word";
    public static final String TABLE_COLUMN2 = "wordtype";
    public static final String TABLE_COLUMN3 = "definition";
    public static final String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"("+
                                                TABLE_COLUMN1+" VARCHAR(25) NOT NULL,"+
                                                TABLE_COLUMN2+" VARCHAR(20) NOT NULL,"+
                                                TABLE_COLUMN3+" VARCHAR NOT NULL)";

    private String DB_PATH;
    private SQLiteDatabase myDataBase;

    private final Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/" + context.getFilesDir().getPath() + "/databases/";

        createDataBase();
        openDataBase();
    }

    public void createDataBase() {

        boolean dbExist = checkDataBase();
        SQLiteDatabase db_Read = null;

        if(dbExist){
        }else{
            db_Read = this.getReadableDatabase();
            db_Read.close();

            try {
                copyDataBase();
            } catch (IOException e) {
                e.toString();
            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch(SQLiteException e){ }

        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException{
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
