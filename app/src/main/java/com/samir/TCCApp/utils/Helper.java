package com.samir.TCCApp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class Helper {

    public void permission(String permission, Context context){
        if (ActivityCompat.checkSelfPermission(context,
                permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]
                    {permission}, 1);
        }
    }

}
