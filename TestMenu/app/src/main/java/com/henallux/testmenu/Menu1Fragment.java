package com.henallux.testmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class Menu1Fragment extends Fragment {

    private ListView listTest;

    public Menu1Fragment() {
        // Required empty public constructor
    }

    public static Menu1Fragment newInstance() {

        Menu1Fragment fragment = new Menu1Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_menu1, container, false);
        listTest = (ListView)fragmentView.findViewById(R.id.listView);

        ArrayList<String> allPerson = new ArrayList<String>();
        allPerson.add("Durant");
        allPerson.add("Dernivoix");
        allPerson.add("Doumont");
        allPerson.add("Degraux");
        allPerson.add("Leonard");
        allPerson.add("Leonard");
        allPerson.add("Muller");
        allPerson.add("Lambert");
        allPerson.add("Lejeune");
        allPerson.add("Hayward");

        Collections.sort(allPerson, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, allPerson);
        listTest.setAdapter(adapter);

        listTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), InformationActivity.class);
                intent.putExtra("infoFragment", "fragment listing");
                intent.putExtra("infoPatient", listTest.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });

        return fragmentView;
    }

}