package com.henallux.testmenu;

import android.content.Context;
import android.content.Intent;
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

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

public class InformationActivity extends AppCompatActivity {

    private String messageFragment;
    private String infoPatient;
    private static String adr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Bundle bundle = this.getIntent().getExtras();
        messageFragment = bundle.getString("infoFragment");
        infoPatient = bundle.getString("infoPatient");
        ((TextView)findViewById(R.id.NomPrenomId)).setText(infoPatient);
        TextView adresse = (TextView)findViewById(R.id.AdresseId);
        adr = (String)adresse.getText();
        adr = adr.replaceAll("\\s", "+");

        /*adresse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(InformationActivity.this,MapsActivity.class));
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + adr + "+Belgium");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });*/
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
        if (id == R.id.action_settings) {
            return true;
        }
        else {
            if(id == R.id.action_location) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + adr + "+Belgium");
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
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
