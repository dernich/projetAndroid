package com.henallux.testmenu;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Menu2Fragment extends Fragment {

    public Menu2Fragment() {
        // Required empty public constructor
    }

    public static Menu2Fragment newInstance() {

        Menu2Fragment fragment = new Menu2Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu2, container, false);
    }

}
