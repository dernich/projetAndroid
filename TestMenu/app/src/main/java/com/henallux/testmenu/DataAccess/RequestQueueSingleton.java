package com.henallux.testmenu.DataAccess;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.henallux.testmenu.MyApplication;

public class RequestQueueSingleton {
    private static RequestQueueSingleton mInstance = null;
    private RequestQueue mRequestQueue;

    private RequestQueueSingleton(){
        mRequestQueue = Volley.newRequestQueue(MyApplication.getInstance());
    }

    public static RequestQueueSingleton getInstance() {
        if(mInstance==null) {
            mInstance = new RequestQueueSingleton();
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}
