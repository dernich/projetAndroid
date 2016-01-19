package com.henallux.testmenu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.henallux.testmenu.DataAccess.AllData;
import com.henallux.testmenu.Model.Care;
import com.henallux.testmenu.Model.Patient;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Menu2Fragment extends Fragment {

    private ListView listTest;
    private ArrayList<Patient> listPatients;
    Gson gson;
    ProgressDialog progressDialog;

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

        getActivity().setTitle(R.string.title_section2);

        gson = new Gson();
        listPatients = new ArrayList<>();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(R.string.loading);
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestQueue requestQueue =  Volley.newRequestQueue(this.getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, "http://nurseapi.azurewebsites.net/api/patients", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transformationResponse(response);
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

        listTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), InformationActivity.class);
                intent.putExtra("infoFragment", "fragment suppression");
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

    private void transformationResponse(String response) {
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
                listPatients.add(p);
            }

            if(!listPatients.isEmpty()) {
                Collections.sort(listPatients, new Comparator<Patient>() {
                    @Override
                    public int compare(Patient s1, Patient s2) {
                        return s1.getNom().compareToIgnoreCase(s2.getNom());
                    }
                });
                ArrayAdapter<Patient> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listPatients);
                listTest.setAdapter(adapter);
                progressDialog.dismiss();
            }

        } catch (JSONException e) {

        }
    }
}
