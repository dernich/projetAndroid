package com.henallux.testmenu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.henallux.testmenu.DataAccess.AllData;
import com.henallux.testmenu.Model.Patient;

import java.sql.Date;
import java.util.ArrayList;

public class InformationActivity extends AppCompatActivity {

    private String messageFragment;
    private Patient patient;
    private String adr;
    private TextView tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Bundle bundle = this.getIntent().getExtras();
        messageFragment = bundle.getString("infoFragment");
        patient = (Patient)bundle.getSerializable("infoPatient");
        ((TextView)findViewById(R.id.NomPrenomId)).setText(patient.getNom() + " " + patient.getPrenom());
        ((TextView)findViewById(R.id.AdresseId)).setText(patient.getRue() + " " + patient.getNumeroMaison());
        ((TextView)findViewById(R.id.LocaliteId)).setText(patient.getCodePostal() + " " + patient.getLocalite());
        ((TextView)findViewById(R.id.TelephoneId)).setText(patient.getNumeroTel());
        ((TextView)findViewById(R.id.DateNaissanceId)).setText(patient.getDateNaissance());
        TextView adresse = (TextView)findViewById(R.id.AdresseId);

        adr = (String)adresse.getText();
        adr = adr.replaceAll("\\s", "+");
        tel = (TextView)findViewById(R.id.TelephoneId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if(messageFragment.equals("fragment suppression")) {
            getMenuInflater().inflate(R.menu.menu_information_suppression, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.menu_information, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_location) {
                TextView localite = (TextView)findViewById(R.id.LocaliteId);
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + adr + " " + (String)localite.getText() + "+Belgium");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (isOnline()) {
                    startActivity(mapIntent);
                }
                else {
                    Fragment objFragment = ErrorConnection.newInstance();
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .replace(R.id.container, objFragment)
                            .commit();
                }
            }
            else {
                if(id == R.id.action_suppression) {
                    //AllData allData = new AllData();
                    //allData.deletePatient(patient);
                    RequestQueue requestQueue =  Volley.newRequestQueue(this.getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.DELETE, "http://nurseapi.azurewebsites.net/api/patients/" + patient.getId(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("onResponse ", response);
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
                    finish();
                }
                else {
                    if(id == R.id.action_call) {
                        android.net.Uri uri = Uri.parse("tel:" + tel.getText());
                        startActivity(new Intent(Intent.ACTION_DIAL, uri));
                    }
                }
            }

        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
