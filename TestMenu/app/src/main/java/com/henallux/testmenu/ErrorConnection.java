package com.henallux.testmenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ErrorConnection extends Fragment {


    public ErrorConnection() {
        // Required empty public constructor
    }

    public static ErrorConnection newInstance() {

        ErrorConnection fragment = new ErrorConnection();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error_connection, container, false);
    }


}
