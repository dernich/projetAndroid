package com.henallux.testmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.henallux.testmenu.Model.Nurse;
import com.henallux.testmenu.Model.Patient;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    //private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mLoginView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    Gson gson;
    Boolean ok = false;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        // Set up the login form.
        mLoginView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        mLoginFormView = findViewById(R.id.login_form);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptLogin();
                mLoginView.setError(null);
                mPasswordView.setError(null);

                // Store values at the time of the login attempt.
                String loginView = mLoginView.getText().toString();
                String password = mPasswordView.getText().toString();

                boolean cancel = false;
                View focusView = null;

                // Check for a valid password, if the user entered one.
                if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                    mPasswordView.setError(getString(R.string.error_invalid_password));
                    focusView = mPasswordView;
                    cancel = true;
                }

                // Check for a valid email address.
                if (TextUtils.isEmpty(loginView)) {
                    mLoginView.setError(getString(R.string.error_field_required));
                    focusView = mLoginView;
                    cancel = true;
                }

                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                } else {
                    String login = mLoginFormView.toString();
                    String mdp = mPasswordView.getText().toString();
                    gson = new Gson();

                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle(R.string.connectionLoad);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this.getApplicationContext());
                    //Log.i("debugTag", "request queue");
                    StringRequest request = new StringRequest(Request.Method.GET, "http://nurseapi.azurewebsites.net/api/infirmiers", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.i("debugTag", mLoginFormView.toString());
                            //Log.i("debugTag", "transfo reponse");
                            transformationResponse(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Log.i("debugTag", error.toString());
                            progressDialog.dismiss();
                        }
                    });
                    request.setRetryPolicy(new DefaultRetryPolicy(
                            30000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    requestQueue.add(request);
                }
            }
        });
        mProgressView = findViewById(R.id.login_progress);
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }


    private void transformationResponse(String response) {
        if(response == null || response.length() == 0) {
            Log.i("debugTag", "reponse null");
            return;
        }

        try {

            Log.i("debugTag", "recup infirmier");
            JSONArray responseJSON = new JSONArray(response);
            //Log.i("debugTag", responseJSON.toString());
            Boolean loginOk = false;
            for (int i = 0; i < responseJSON.length(); i++) {
                Nurse n = new Nurse();
                n.setId(responseJSON.getJSONObject(i).getInt("idinfirmier"));
                n.setNom(responseJSON.getJSONObject(i).getString("nom"));
                n.setPrenom(responseJSON.getJSONObject(i).getString("prenom"));
                n.setEmail(responseJSON.getJSONObject(i).getString("email"));
                n.setNumeroTel(responseJSON.getJSONObject(i).getString("telephone"));
                n.setTypeChef(responseJSON.getJSONObject(i).getString("type"));
                n.setLogin(responseJSON.getJSONObject(i).getString("login"));
                n.setPassword(responseJSON.getJSONObject(i).getString("motDePasse"));
                //Log.i("debugTag", responseJSON.getJSONObject(i).getString("login") + " " + responseJSON.getJSONObject(i).getString("motDePasse"));
                if(n != null) {
                    if (n.getLogin().equals(mLoginView.getText().toString())) {
                        //Log.i("debugTag",mPasswordView.getText().toString());
                        loginOk = true;
                        if(n.getPassword().equals(mPasswordView.getText().toString())) {
                            progressDialog.dismiss();
                            //Log.i("debugTag", n.getLogin() + " " + n.getPassword());
                            //System.out.println("Id connecté" + n.getId());
                            MyApplication.setIdInfirmiere(n);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                        else {
                            mPasswordView.setError(getString(R.string.error_invalid_password));
                            progressDialog.dismiss();
                        }
                    }
                }
            }
            if(!loginOk) {
                mLoginView.setError(getString(R.string.error_invalid_login));
                progressDialog.dismiss();
            }
        }
        catch (JSONException e) { }
    }
}

