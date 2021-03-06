package com.henallux.testmenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.henallux.testmenu.Model.Nurse;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Boolean clickDeconnexion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickDeconnexion = false;
        Fragment objFragment = null;

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        if(isOnline()) {
            Toast.makeText(this, R.string.toastConnection, Toast.LENGTH_LONG).show();
        }
        else {
            objFragment = ErrorConnection.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.container, objFragment)
                    .commit();
            Toast.makeText(this, R.string.toastNoConnection, Toast.LENGTH_LONG).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment objFragment = null;
        ActionBar actionBar = getSupportActionBar();

        if (isOnline()) {
            String chef = MyApplication.getIdInfirmiere().getTypeChef();
            if(chef.equals("true")) {
                switch (position) {
                    case 0:
                        objFragment = Menu1Fragment.newInstance();
                        actionBar.setTitle(R.string.title_section1);
                        break;
                    case 1:
                        objFragment = Menu2Fragment.newInstance();
                        actionBar.setTitle(R.string.title_section2);
                        break;
                    case 2:
                        objFragment = Menu3Fragment.newInstance();
                        actionBar.setTitle(R.string.title_section3);
                        break;
                    case 3:
                        MyApplication.setIdInfirmiere(null);
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        clickDeconnexion = true;
                        finish();
                        break;
                }
            }
            else {
                switch (position) {
                    case 0:
                        objFragment = Menu1Fragment.newInstance();
                        actionBar.setTitle(R.string.title_section1);
                        break;
                    case 1:
                        MyApplication.setIdInfirmiere(null);
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        clickDeconnexion = true;
                        finish();
                        break;
                }
            }
        }
        else {
            objFragment = ErrorConnection.newInstance();
        }
        if(!clickDeconnexion) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.container, objFragment)
                    .commit();
        }
        //Log.i("debugTag", "transaction is executed");
    }

    public void onSectionAttached(int number) {
        String chef = MyApplication.getIdInfirmiere().getTypeChef();
        if(chef.equals("true")) {
            switch (number) {
                case 1:
                    mTitle = getString(R.string.title_section1);
                    break;
                case 2:
                    mTitle = getString(R.string.title_section2);
                    break;
                case 3:
                    mTitle = getString(R.string.title_section3);
                    break;
                case 4:
                    mTitle = getString(R.string.title_section4);
                    break;
            }
        }
        else {
            switch (number) {
                case 1:
                    mTitle = getString(R.string.title_section1);
                    break;
                case 2:
                    mTitle = getString(R.string.title_section4);
                    break;
            }
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            Activity activity = (Activity)context;
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
