package com.henallux.testmenu;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.henallux.testmenu.Model.Nurse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static Nurse idInfirmiere;

    @Override
    public void onCreate(){
        super.onCreate();

        try { PackageInfo info = getPackageManager().getPackageInfo( this.getPackageName(), PackageManager.GET_SIGNATURES); for (Signature signature : info.signatures) { MessageDigest md = MessageDigest.getInstance("SHA"); md.update(signature.toByteArray()); Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT)); } } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {e.printStackTrace(); }

        mInstance = this;
    }

    public static MyApplication getInstance(){
        return mInstance;
    }

    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }

    public static void setIdInfirmiere(Nurse id) {
        idInfirmiere = id;
    }
    public static Nurse getIdInfirmiere() {
        return idInfirmiere;
    }
}
