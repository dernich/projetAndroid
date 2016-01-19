package com.henallux.testmenu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.henallux.testmenu.DataAccess.AllData;
import com.henallux.testmenu.DataAccess.RequestQueueSingleton;
import com.henallux.testmenu.Model.Care;
import com.henallux.testmenu.Model.Nurse;
import com.henallux.testmenu.Model.Patient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Menu3Fragment extends Fragment {

    private Spinner nurseChoice;
    private Spinner patientChoice;
    private DatePicker dateChoice;
    private Button attributionButton;
    private int patientSelected;
    private int nurseSelected;
    private ArrayList<Nurse> allNurse;
    private ArrayList<Patient> allPatient;
    private String dateAjout;
    Gson gson;
    ProgressDialog progressDialog;

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

        gson = new Gson();
        allNurse = new ArrayList<>();
        allPatient = new ArrayList<>();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(R.string.loading);
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestQueue requestQueue =  Volley.newRequestQueue(this.getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, "http://nurseapi.azurewebsites.net/api/infirmiers", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transformationResponseNurse(response);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onErrorResponse()", error.toString());
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);

        nurseChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nurseSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner Patient
        patientChoice = (Spinner) fragmentView.findViewById(R.id.spinnerPatient);

        request = new StringRequest(Request.Method.GET, "http://nurseapi.azurewebsites.net/api/patients", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transformationResponsePatient(response);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onErrorResponse()", error.toString());
                progressDialog.dismiss();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);

        patientChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                patientSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateChoice = (DatePicker)fragmentView.findViewById(R.id.datePicker);
        dateChoice.setMinDate(System.currentTimeMillis() - 1000);

        attributionButton = (Button) fragmentView.findViewById(R.id.buttonValidation);
        attributionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = dateChoice.getDayOfMonth();
                int month = dateChoice.getMonth();
                int year = dateChoice.getYear();
                final Calendar calendar = Calendar.getInstance();
                calendar.set(day, month, year);
                final Care care = new Care();
                care.setIdNurse(allNurse.get(nurseSelected).getId());
                care.setIdPatient(allPatient.get(patientSelected).getId());
                care.setCareDate(day + "-" + month + "-" + year);
                JSONObject params = new JSONObject();
                try {
                    params.put("dateSoin", care.getCareDate());
                    params.put("idInfi", care.getIdNurse());
                    params.put("idPatient", care.getIdPatient());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://nurseapi.azurewebsites.net/api/soins", params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("onResponse()", response.toString());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(R.string.addCareTitle);
                        builder.setMessage(R.string.addCareConfirmation);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("onErrorResponse()", error.toString());
                    }
                });
                request.setRetryPolicy(new DefaultRetryPolicy(
                        20000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);
            }
        });

        return fragmentView;
    }

    private void transformationResponseNurse(String response) {
        if(response == null || response.length() == 0) {

            return;
        }

        try {

            JSONArray responseJSON = new JSONArray(response);
            for (int i = 0; i < responseJSON.length(); i++) {
                Nurse n = new Nurse();
                n.setId(responseJSON.getJSONObject(i).getInt("idinfirmier"));
                n.setNom(responseJSON.getJSONObject(i).getString("nom"));
                n.setPrenom(responseJSON.getJSONObject(i).getString("prenom"));
                n.setEmail(responseJSON.getJSONObject(i).getString("email"));
                n.setNumeroTel(responseJSON.getJSONObject(i).getString("telephone"));
                n.setLogin(responseJSON.getJSONObject(i).getString("login"));
                n.setPassword(responseJSON.getJSONObject(i).getString("motDePasse"));
                n.setTypeChef(responseJSON.getJSONObject(i).getString("type"));
                allNurse.add(n);
            }

            if(!allNurse.isEmpty()) {
                ArrayList<String> listSpinnerNurse = new ArrayList<String>();
                for(Nurse nurse : allNurse) {
                    listSpinnerNurse.add(nurse.getNom() + " " + nurse.getPrenom());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listSpinnerNurse);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                nurseChoice.setAdapter(adapter);
            }

        } catch (JSONException e) {

        }
    }

    private void transformationResponsePatient(String response) {
        if(response == null || response.length() == 0) {

            return;
        }

        try {

            JSONArray responseJSON = new JSONArray(response);
            for (int i = 0; i < responseJSON.length(); i++) {
                Patient p = new Patient();
                p.setId(responseJSON.getJSONObject(i).getInt("idpatient"));
                p.setNom(responseJSON.getJSONObject(i).getString("nom"));
                p.setPrenom(responseJSON.getJSONObject(i).getString("prenom"));
                p.setDateNaissance(responseJSON.getJSONObject(i).getString("dateNaissance"));
                p.setRue(responseJSON.getJSONObject(i).getString("rue"));
                p.setNumeroMaison(responseJSON.getJSONObject(i).getString("numeroMaison"));
                p.setLocalite(responseJSON.getJSONObject(i).getString("localite"));
                p.setCodePostal(responseJSON.getJSONObject(i).getString("codePostal"));
                p.setNumeroTel(responseJSON.getJSONObject(i).getString("numTelephone"));
                p.setDateDebutSoin(responseJSON.getJSONObject(i).getString("dateDebutSoin"));
                p.setDateFinSoin(responseJSON.getJSONObject(i).getString("dateFinSoin"));
                allPatient.add(p);
            }

            if(!allPatient.isEmpty()) {
                ArrayList<String> listSpinnerPatient = new ArrayList<String>();
                for(Patient patient : allPatient) {
                    listSpinnerPatient.add(patient.getNom() + " " + patient.getPrenom());
                }

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listSpinnerPatient);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                patientChoice.setAdapter(adapter2);
            }

        } catch (JSONException e) {

        }
    }
}
