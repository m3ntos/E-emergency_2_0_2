package com.pp.rafix.e_emergency_2_0_2.database;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Rafix on 2014-06-01.
 */
public class MyDatabaseOpenHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "e_mergencydb.sqlite";
    private static final int DATABASE_VERSION = 1;

    private  static MyDatabaseOpenHelper instance=null;

    private MyDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static MyDatabaseOpenHelper getInstance(Context context){
        if(instance==null){
            instance= new MyDatabaseOpenHelper(context);
        }
        return instance;
    }
}
