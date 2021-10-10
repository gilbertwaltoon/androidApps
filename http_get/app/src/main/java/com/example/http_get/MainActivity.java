package com.example.http_get;

import androidx.appcompat.app.AppCompatActivity;

// import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyGet";

    String resp = "nothing yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Http_get);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.text);

        RequestQueue queue = Volley.newRequestQueue(this);

        //String url = "https://www.google.com";
        String url = "http://192.168.1.207/hello?q=wibble";
        // String url = "http://neverssl.com"; // HTTP works also  - but see
        // https://stackoverflow.com/questions/51902629/how-to-allow-all-network-connection-types-http-and-https-in-android-9-pie

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        resp = new String(response.substring(0, Math.min(20, response.length())));
                        textView.setText("Got data" + resp);
                        Log.d(TAG, "Response is: " + response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Get data failed");
                Log.e(TAG, "volley error");
                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                body = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                Log.e(TAG, "volley error: " + body.substring(0, Math.min(20, body.length())));
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
        Log.d(TAG, "Added get to q ");

    }
}

