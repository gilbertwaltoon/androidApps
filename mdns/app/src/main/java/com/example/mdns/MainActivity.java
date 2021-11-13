package com.example.mdns;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    NsdHelper mNsdHelper;

    public static final String TAG = "NSD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mNsdHelper = new NsdHelper(this);
        mNsdHelper.initializeNsd();
        Log.d(TAG, "MAIN back from initilizeNsd.");
        mNsdHelper.discoverServices();
        Log.d(TAG, "MAIN back from discoverServices");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MAIN onResume");
        if (mNsdHelper != null) {
            mNsdHelper.initializeNsd();
            mNsdHelper.discoverServices();
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "MAIN onDestroy");
        mNsdHelper.tearDown();
        super.onDestroy();
    }

}

