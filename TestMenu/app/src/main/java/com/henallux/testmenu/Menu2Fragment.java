package com.henallux.testmenu;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Menu2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Menu2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu2Fragment extends Fragment {

    public Menu2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu2, container, false);
    }

}
