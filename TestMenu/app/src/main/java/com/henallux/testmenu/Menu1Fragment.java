package com.henallux.testmenu;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.henallux.testmenu.Model.Patient;

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


        ArrayList<Patient> allPatient = new ArrayList<Patient>();
        allPatient.add(new Patient("Dernivoix", "Antoine", "09/11/1993", "0494089554", "Place de la gare", 7, "6840", "Longlier"));
        allPatient.add(new Patient("Doumont", "Kévin", "02/01/1995", "0497403226", "Longtry", 119, "5070", "Leroux"));
        allPatient.add(new Patient("Hayward", "Juliette", "10/01/1992", "0494465836", "Avenue Roi baudouin", 15, "6600", "Bastogne"));
        allPatient.add(new Patient("Leonard", "Sébastien", "15/06/1995", "0475761904", "route d'onhaye", 17, "5524", "Dinant"));
        allPatient.add(new Patient("Degraux", "Maxence", "16/06/1995", "0498139358", "rue du monument", 108, "5620", "Rosée"));

        Collections.sort(allPatient, new Comparator<Patient>() {
            @Override
            public int compare(Patient s1, Patient s2) {
                return s1.getNom().compareToIgnoreCase(s2.getNom());
            }
        });
        ArrayAdapter<Patient> adapter = new ArrayAdapter<Patient>(getActivity(), android.R.layout.simple_list_item_1, allPatient);
        listTest.setAdapter(adapter);

        listTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), InformationActivity.class);
                intent.putExtra("infoFragment", "fragment listing");
                Patient patientSelected = (Patient)listTest.getItemAtPosition(position);
                intent.putExtra("infoPatient", patientSelected);
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