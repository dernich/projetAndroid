package com.henallux.testmenu;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Menu3Fragment extends Fragment {

    private Spinner nurseChoice;
    private Spinner patientChoice;
    private DatePicker dateChoice;

    public Menu3Fragment() {
        // Required empty public constructor
    }

    public static Menu3Fragment newInstance() {

        Menu3Fragment fragment = new Menu3Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Spinner Nurse
        View fragmentView = inflater.inflate(R.layout.fragment_menu3, container, false);
        nurseChoice = (Spinner)fragmentView.findViewById(R.id.spinnerNurse);

        ArrayList<String> listSpinner = new ArrayList<String>();
        listSpinner.add("Lambert Christine");
        listSpinner.add("Preser Aline");
        listSpinner.add("Hayward Sarah");
        listSpinner.add("Vartan Jeanne");
        listSpinner.add("Arnould Tom");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nurseChoice.setAdapter(adapter);

        //Spinner Patient
        patientChoice = (Spinner)fragmentView.findViewById(R.id.spinnerPatient);

        ArrayList<String> listSpinnerPatient = new ArrayList<String>();
        listSpinnerPatient.add("Durant Charles");
        listSpinnerPatient.add("Koulibaly Abdel");
        listSpinnerPatient.add("Meunier Thomas");
        listSpinnerPatient.add("Muller Thomasz");
        listSpinnerPatient.add("Dupont Géraldine");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listSpinnerPatient);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientChoice.setAdapter(adapter2);

        dateChoice = (DatePicker)fragmentView.findViewById(R.id.datePicker);
        /*Date date = new Date();
        String formats = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Log.i("debugTag", sdf.format(date));

        dateChoice.setMinDate(date.);*/
        dateChoice.setMinDate(System.currentTimeMillis() - 1000);

        return fragmentView;
    }
}
