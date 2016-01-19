package com.henallux.testmenu;

import android.app.ProgressDialog;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.henallux.testmenu.DataAccess.AllData;
import com.henallux.testmenu.DataAccess.RequestQueueSingleton;
import com.henallux.testmenu.Model.Care;
import com.henallux.testmenu.Model.Patient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Menu1Fragment extends Fragment {

    private ListView listTest;
    private ArrayList<Patient> listPatients;
    Gson gson;
    ProgressDialog progressDialog;

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

        getActivity().setTitle(R.string.title_section1);

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
                Log.i("debugTag","recup patients");
                transformationResponse(response);
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
                intent.putExtra("infoFragment", "fragment listing");
                Patient patientSelected = (Patient) listTest.getItemAtPosition(position);
                intent.putExtra("infoPatient", patientSelected);
                if (isOnline()) {
                    startActivity(intent);
                } else {
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

    private void transformationResponse(String response) {
        if(response == null || response.length() == 0) {

            return;
        }

        try {
            JSONArray responseJSON = new JSONArray(response);
            for (int i = 0; i < responseJSON.length(); i++) {
                Patient p = new Patient();
                //Log.i("debugTag", "Nouveau patient");
                p.setId(responseJSON.getJSONObject(i).getInt("idpatient"));
                p.setNom(responseJSON.getJSONObject(i).getString("nom"));
                p.setPrenom(responseJSON.getJSONObject(i).getString("prenom"));
                p.setDateNaissance(responseJSON.getJSONObject(i).getString("dateNaissance"));
                p.setRue(responseJSON.getJSONObject(i).getString("rue"));
                p.setNumeroMaison(responseJSON.getJSONObject(i).getString("numeroMaison"));
                p.setLocalite(responseJSON.getJSONObject(i).getString("localite"));
                p.setCodePostal(responseJSON.getJSONObject(i).getString("codePostal"));
                p.setNumeroTel(responseJSON.getJSONObject(i).getString("numTelephone"));
                JSONArray tabSoins = new JSONArray(response).getJSONObject(i).getJSONArray("soin");
                //Log.i("debugTag", "soin");
                for (int j = 0; j < tabSoins.length(); j++) {
                    Care c = new Care();
                    Date dateDay = new Date();
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                    String date = df.format(dateDay);
                    //Log.i("debugTag", date);
                    if(tabSoins.getJSONObject(j).getString("dateSoin").equals(date)) {
                        //Log.i("debugTag", "soin du 18-12-2015");
                        JSONObject n = tabSoins.getJSONObject(j).getJSONObject("infirmier");
                        //Log.i("debugTag", n.getString("nom"));
                        if (n.getInt("idinfirmier") == MyApplication.getIdInfirmiere().getId()) {
                            //Log.i("debugTag",p.getNom());
                            listPatients.add(p);
                        }
                    }

                }
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

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}