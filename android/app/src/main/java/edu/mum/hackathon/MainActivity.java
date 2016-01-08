package edu.mum.hackathon;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    MyLifeRecord db;
    RequestQueue syncQueue;
    //Button sleepButton;
    //Button meditationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        setContentView(R.layout.content_main);
        /*sleepButton = (Button)findViewById(R.id.sleepButton);
        meditationButton = (Button)findViewById(R.id.meditationButton);*/
        db = new MyLifeRecord(MainActivity.this);

        syncQueue = Volley.newRequestQueue(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void goToSleepPage(View view) {
        setContentView(R.layout.sleep_activity);
    }

    public void goHome(View view) {
        setContentView(R.layout.content_main);
    }

    public void goToMeditationPage(View view) {
        setContentView(R.layout.meditation_activity);
    }

    Date sleepStart = null;
    public void onSleepingNow(View view) {
        sleepStart = new Date();
    }

    public void onJustWokeUp(View view) {
        if(sleepStart == null)
            return;
        Date now = new Date();
        long duration = now.getTime() - sleepStart.getTime();
        long minutes = duration/60;
        db.insert(1, sleepStart, minutes);
    }

    Date meditationStart = null;
    public void onMeditationClosingEyes(View view) {
        meditationStart = new Date();
    }

    public void onMeditationOpeningEyes(View view) {
        if(meditationStart == null)
            return;
        Date now = new Date();
        long duration = now.getTime() - meditationStart.getTime();
        long minutes = duration/60;
        db.insert(2, meditationStart, minutes);
    }

    final static String SITE_URL = "http://54.200.141.172/?q=";
    final static String LOGIN_URL = SITE_URL + "/chakraapi/user/login";
    final static String SESSION_TOKEN_URL = SITE_URL + "/services/session/token";
    String sessionToken = null;

    public void webProceedWithKey() {
        if(sessionToken != null)
            webStartLogin();
        final Button sinkButton = (Button)findViewById(R.id.syncButton);
        // Instantiate the RequestQueue
        //String url ="http://www.google.com";
        String url = SESSION_TOKEN_URL;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        sessionToken = response;
                        sinkButton.setText(R.string.SigningIn);
                        Log.i("SESSION", sessionToken);
                        webStartLogin();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sinkButton.setText(R.string.SyncingFailed);
            }
        });
        // Add the request to the RequestQueue.
        syncQueue.add(stringRequest);
    }

    private final String USERNAME = "username";
    private final String PASSWORD = "password";

    public void webStartLogin() {
        final Button sinkButton = (Button)findViewById(R.id.syncButton);
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="http://www.google.com";
        String url = LOGIN_URL;
        JSONObject req = new JSONObject();
        try {
            req.put(USERNAME, "kamanashisroy");
            req.put(PASSWORD, "chakra");
        } catch (JSONException e) {
            e.printStackTrace();
            sinkButton.setText(R.string.SyncingFailed);
            return;
        }

        // Request a string response from the provided URL.
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, req,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        sinkButton.setText(R.string.SyncingSuccessful);
                        //sinkButton.setText(R.string.Syncing);
                        Log.i("LOGIN", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sinkButton.setText(R.string.SyncingFailed);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("X-CSRF-Token", sessionToken);
                //params.put("Content-Type", "application/json");
                return params;
            }
        };
        // Add the request to the RequestQueue.
        syncQueue.add(loginRequest);
    }

    public void onSync(View view) {
        webProceedWithKey();
    }
}
