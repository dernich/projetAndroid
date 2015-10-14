package com.henallux.testmenu;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Menu3Fragment extends Fragment {

    public Menu3Fragment() {
        // Required empty public constructor
    }

    public static Menu3Fragment newInstance() {

        Menu3Fragment fragment = new Menu3Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu3, container, false);
    }

}
