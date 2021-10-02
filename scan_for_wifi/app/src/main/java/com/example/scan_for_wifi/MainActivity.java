package com.example.scan_for_wifi;

// https://github.com/espressif/esp-idf-provisioning-android.git

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyApp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();
        // See if internet connected
        boolean isconn = isInternetConnected(context);
        Log.v(TAG, "isInternetConnected = " + isconn);
        // See if wifi connected
        isconn = isWifiConnected();
        Log.v(TAG, "isWifiConnected = " + isconn);
        // Get details of wifi connection status
        wifiStatusDetail();
        // See if Gigaclear connected
        isGigaclearConnected();
        //
        Button button = (Button) findViewById(R.id.start_button);
        button.setOnClickListener(start_button_clicklistener);
    }

    private final View.OnClickListener start_button_clicklistener = new View.OnClickListener() {
        public void onClick(View v) {
            Log.v(TAG, "start_button clicked");
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivity(intent);
        }
    };

    public static boolean isInternetConnected(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                Log.v(TAG, "connected to internet");
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Log.v(TAG, "connected to mobile data");
                return true;
            }
        } else {
            Log.v(TAG, "not connected");
        }
        return false;
    }

    public boolean isWifiConnected() {
        Log.v(TAG, "isWifiConnected ?");
        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        if (wifiMgr.isWifiEnabled()) { // Wi-Fi adapter is ON
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            if (wifiInfo.getNetworkId() == -1) {
                Log.v(TAG, " Not connected to an access point");
                return false; // Not connected to an access point
            }
            Log.v(TAG, " Connected to an access point");
            return true; // Connected to an access point
        } else {
            Log.v(TAG, " Wifi adapter is OFF");
            return false; // Wi-Fi adapter is OFF
        }
    }

    public void wifiStatusDetail() {
        //stackoverflow.com/questions/3841317/how-do-i-see-if-wi-fi-is-connected-on-android
        Log.v(TAG, "isWifiConnectedDetailed");
        SupplicantState supState;
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        supState = wifiInfo.getSupplicantState();
        if (supState == SupplicantState.ASSOCIATED) {
            Log.v(TAG, "ASSOCIATED");
        } else if (supState == SupplicantState.ASSOCIATING) {
            Log.v(TAG, "ASSOCIATING");
        } else if (supState == SupplicantState.COMPLETED) {
            Log.v(TAG, "COMPLETED - all authentification completed");
        } else if (supState == SupplicantState.DISCONNECTED) {
            Log.v(TAG, "DISCONNECTED - client is not associated, but likely to start looking for an access point");
        } else if (supState == SupplicantState.DORMANT) {
            Log.v(TAG, "DORMANT - reported when client issues explicit disconnect");
        } else if (supState == SupplicantState.FOUR_WAY_HANDSHAKE) {
            Log.v(TAG, "FOUR_WAY_HANDSHAKE - WPA 4-Way Key Handshake in progress");
        } else if (supState == SupplicantState.GROUP_HANDSHAKE) {
            Log.v(TAG, "GROUP_HANDSHAKE  - WPA Group Key Handshake in progress");
        } else if (supState == SupplicantState.INACTIVE) {
            Log.v(TAG, "INACTIVE");
        } else if (supState == SupplicantState.INVALID) {
            Log.v(TAG, "INVALID - pseudo-state, normally not seen");
        } else if (supState == SupplicantState.SCANNING) {
            Log.v(TAG, "SCANNING");
        } else if (supState == SupplicantState.UNINITIALIZED) {
            Log.v(TAG, "UNINITIALIZED - No conn");
        } else {
            Log.v(TAG, "Unknown response");
        }
    }

    public void isGigaclearConnected() {
        SupplicantState supState;
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        supState = wifiInfo.getSupplicantState();
        if (supState == SupplicantState.COMPLETED) {
            String ssid = wifiInfo.getSSID();
            if (ssid.contains("clear")) {
                Log.v(TAG, "Found gigaclear connected");
            } else {
                Log.v(TAG, "gigaclear not found");
            }
        }
    }

}