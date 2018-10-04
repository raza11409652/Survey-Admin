package com.example.mdkhalidrazakhan.surveyprojectadmin.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME="survey.db";
    public static final String TABLE_NAME="survey_report";
    public static  final String COL_1="ID";
    public static  final String COL_2="AddMandal";
    public static  final String COL_3="AddSubVill";
    public static  final String COL_4="FullAddress";
    public static  final String COL_5="AddMob";
    public static  final String COL_6="Log";
    public static  final String COL_7="Sex";
    public static  final String COL_8="Rating";
    public static  final String COL_9="AddPin";
    public static  final String COL_10="Date";
    public static  final String COL_11="Name";
    public static  final String COL_12="ConstPrefRating";
    public static  final String COL_13="Profession";
    public static  final String COL_14="Goverment";
    public static  final String COL_15="AddVill";
    public static  final String COL_16="Caste";
    public static  final String COL_17="Age";
    public static  final String COL_18="Community";
    public static  final String COL_19="Lat";
    public static  final String COL_20="ConstTdp";
    public static  final String COL_21="SurveyBy";



    public DataBaseHelper(Context context) {
        super(context, DB_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,MARKS INTEGER)");
        db.execSQL("create table "+TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT ,AddMandal TEXT, AddSubVill TEXT , FullAddress TEXT , AddMob TEXT , Log TEXT,Sex TEXT,Rating TEXT , " +
                "AddPin TEXT , Date TEXT , Name TEXT, ConstPrefRating TEXT , Profession TEXT, Goverment TEXT , AddVill TEXT , Caste TEXT, Age Text , Community TEXT,Lat TEXT ,ConstTdp TEXT,SurveyBy TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String AddMandal,String AddSubVill,String FullAddress,String AddMob,String Log,String Sex,String Rating,
                              String AddPin,String Date, String Name,String ConstPrefRating,String Profession , String Goverment,String AddVill,String Caste,
                              String Age,String Community,String Lat,String ConstTdp,String SurveyBy
    ) {

        /*
        * public static  final String COL_2="AddMandal";
    public static  final String COL_3="AddSubVill";
    public static  final String COL_4="FullAddress";
    public static  final String COL_5="AddMob";
    public static  final String COL_6="Log";
    public static  final String COL_7="Sex";
    public static  final String COL_8="Rating";
    public static  final String COL_9="AddPin";
    public static  final String COL_10="Date";
    public static  final String COL_11="Name";
    public static  final String COL_12="ConstPrefRating";
    public static  final String COL_13="Profession";
    public static  final String COL_14="Goverment";
    public static  final String COL_15="AddVill";
    public static  final String COL_16="Caste";
    public static  final String COL_17="Age";
    public static  final String COL_18="Community";
    public static  final String COL_19="Lat";
    public static  final String COL_20="ConstTdp";
        *
        * */
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,AddMandal);
        contentValues.put(COL_3,AddSubVill);
        contentValues.put(COL_4,FullAddress);
        contentValues.put(COL_5,AddMob);
        contentValues.put(COL_6,Log);
        contentValues.put(COL_7,Sex);
        contentValues.put(COL_8,Rating);
        contentValues.put(COL_9,AddPin);
        contentValues.put(COL_10,Date);
        contentValues.put(COL_11,Name);
        contentValues.put(COL_12,ConstPrefRating);
        contentValues.put(COL_13,Profession);
        contentValues.put(COL_14,Goverment);
        contentValues.put(COL_15,AddVill);
        contentValues.put(COL_16,Caste);
        contentValues.put(COL_17,Age);
        contentValues.put(COL_18,Community);
        contentValues.put(COL_19,Lat);
        contentValues.put(COL_20,ConstTdp);
        contentValues.put(COL_21,SurveyBy);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id,String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
    public void deleteAllData()
    {
        SQLiteDatabase db =this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }
}
