package com.henallux.testmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class Menu1Fragment extends Fragment {

    private ListView listTest;

    public Menu1Fragment() {
        // Required empty public constructor
    }

    public static Menu1Fragment newInstance() {

        Menu1Fragment fragment = new Menu1Fragment();
        Log.i("debugTag", "Constructeur MenuFragment is executed");
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_menu1, container, false);
        listTest = (ListView)fragmentView.findViewById(R.id.listView);

        ArrayList<String> allPerson = new ArrayList<String>();
        Log.i("debugTag", "Array list creation");
        allPerson.add("Durant");
        allPerson.add("Dupont");
        allPerson.add("Dernivoix");
        allPerson.add("Doumont");
        allPerson.add("Degraux");
        allPerson.add("Leonard");
        Log.i("debugTag", "Array list fin ajout et debut adapter");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, allPerson);
        Log.i("debugTag" , "adapter creer");
        listTest.setAdapter(adapter);
        Log.i("debugTag", "ajout adapter");

        Log.i("debugTag", "onCreateView MenuFragment FIN");
        return fragmentView;
    }

}