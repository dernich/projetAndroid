package com.henallux.testmenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Menu2Fragment extends Fragment {

    private ListView listTest;

    public Menu2Fragment() {
        // Required empty public constructor
    }

    public static Menu2Fragment newInstance() {

        Menu2Fragment fragment = new Menu2Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_menu1, container, false);
        listTest = (ListView)fragmentView.findViewById(R.id.listView);

        ArrayList<String> allPerson = new ArrayList<String>();
        allPerson.add("Durant");
        allPerson.add("Dupont");
        allPerson.add("Dernivoix");
        allPerson.add("Doumont");
        allPerson.add("Degraux");
        allPerson.add("Leonard");
        allPerson.add("Leonard");
        allPerson.add("Jacques");
        allPerson.add("Gonry");
        allPerson.add("Burt");
        allPerson.add("Muller");
        allPerson.add("Regis");
        allPerson.add("Lambert");
        allPerson.add("Lejeune");
        allPerson.add("Levieux");
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
                Intent intent = new Intent(getActivity(),InformationActivity.class);
                intent.putExtra("infoFragment", "fragment suppression");
                intent.putExtra("infoPatient", listTest.getItemAtPosition(position).toString());
                if (isOnline()) {
                    startActivity(intent);
                }
                else {
                    Fragment objFragment = ErrorConnection.newInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, objFragment)
                            .commit();
                }
            }
        });
        return fragmentView;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
