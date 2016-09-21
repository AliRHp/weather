package com.hp.alirezas.weather01.utility;

import android.app.Application;

import com.hp.alirezas.weather01.db.DBOperation;

/**
 * Created by alireza s on 6/25/2016.
 */
public class MyApp extends Application {
    private static DBOperation operatin;

    public void onCreate() {
        super.onCreate();
        operatin = new DBOperation(this);

    }

    public static DBOperation getOperatin() {
        return operatin;
    }
}
