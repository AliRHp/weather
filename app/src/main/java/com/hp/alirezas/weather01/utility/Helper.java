package com.hp.alirezas.weather01.utility;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by alireza s on 6/24/2016.
 */
public class Helper {


    public static void showToast(Context context, String title) {
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show();


    }

}
