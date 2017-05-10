package com.sqlite.msk.sqlitefunctionsapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DELL on 5/4/2017.
 */

public class UserDbHelper extends SQLiteOpenHelper {


    public static final String TAG = UserDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String SQL_CREATE_USER_TABLE = "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " ("
                + UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserContract.UserEntry.Column_name + " TEXT NOT NULL, "
                + UserContract.UserEntry.Column_age + " INTEGER NOT NULL, "
                + UserContract.UserEntry.Column_email + " TEXT NOT NULL, "
                +UserContract.UserEntry.Column_gender + " INTEGER NOT NULL, "
                + UserContract.UserEntry.Column_Img + " TEXT NOT NULL); ";

        db.execSQL(SQL_CREATE_USER_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME);

        // create new table
        onCreate(db);
    }


    public boolean addUser(String name, String age, String email, String image, int gender){

          int Age= Integer.parseInt(age);
          String Image = image.toString();

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UserContract.UserEntry.Column_name,name);
        values.put(UserContract.UserEntry.Column_age,Age);
        values.put(UserContract.UserEntry.Column_email,email);
        values.put(UserContract.UserEntry.Column_gender,gender);
        values.put(UserContract.UserEntry.Column_Img,image);



        long newRowId = db.insert(UserContract.UserEntry.TABLE_NAME,null,values);

        if(newRowId==-1){

            return false;


        }else {

            return true;

        }
    }


    public boolean updateUser(Long id,String name, String age, String email, String image, int gender){

        int Age= Integer.parseInt(age);
        String Image = image.toString();

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UserContract.UserEntry.Column_name,name);
        values.put(UserContract.UserEntry.Column_age,Age);
        values.put(UserContract.UserEntry.Column_email,email);
        values.put(UserContract.UserEntry.Column_gender,gender);
        values.put(UserContract.UserEntry.Column_Img,image);

        db.update(UserContract.UserEntry.TABLE_NAME, values, UserContract.UserEntry._ID + " = ? ", new String[] { Long.toString(id) } );


            return true;

        }


    public Cursor getallUsers(){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor userCursor =  db.rawQuery("SELECT * FROM "+ UserContract.UserEntry.TABLE_NAME,null);
        return  userCursor;

    }

//    public Cursor getPerson(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery("SELECT * FROM " + PERSON_TABLE_NAME + " WHERE " +
//                PERSON_COLUMN_ID + "=?", new String[]{Integer.toString(id)});
//        return res;
//    }


    public Cursor getUser(Long id){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(UserContract.UserEntry.TABLE_NAME, new String[] {UserContract.UserEntry._ID,
                        UserContract.UserEntry.Column_name,
                        UserContract.UserEntry.Column_age, UserContract.UserEntry.Column_email, UserContract.UserEntry.Column_gender
                        , UserContract.UserEntry.Column_Img}, UserContract.UserEntry._ID + "=?",
                new String[] { Long.toString(id) }, null, null, null, null);


//        Cursor cursor = db.rawQuery("SELECT * FROM " + UserContract.UserEntry.TABLE_NAME + " WHERE " +
//                UserContract.UserEntry._ID + " = ? " , new  String[] {Long.toString(id)});

        return cursor;
    }


    public Integer deleteUser(Long id){

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(UserContract.UserEntry.TABLE_NAME,
                UserContract.UserEntry._ID + " = ?",
                new String[] {Long.toString(id)});


    }


}
