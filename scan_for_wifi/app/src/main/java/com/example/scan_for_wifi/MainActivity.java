package com.example.scan_for_wifi;

// https://github.com/espressif/esp-idf-provisioning-android.git

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.espressif.provisioning.ESPProvisionManager;
import com.espressif.provisioning.WiFiAccessPoint;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Handler handler;
    private ImageView ivRefresh;
    private ListView wifiListView;
    private ArrayList<WiFiAccessPoint> wifiAPList;
    private ProgressBar progressBar;
    private ESPProvisionManager provisionManager;
    private WiFiListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_wifi_scan_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_wifi_scan_list);
        setSupportActionBar(toolbar);

        ivRefresh = findViewById(R.id.btn_refresh);
        wifiListView = findViewById(R.id.wifi_ap_list);
        progressBar = findViewById(R.id.wifi_progress_indicator);

        progressBar.setVisibility(View.VISIBLE);

        wifiAPList = new ArrayList<>();
        handler = new Handler();
        provisionManager = ESPProvisionManager.getInstance(getApplicationContext());

        String deviceName = provisionManager.getEspDevice().getDeviceName();
        String wifiMsg = String.format(getString(R.string.setup_instructions), deviceName);
        TextView tvWifiMsg = findViewById(R.id.wifi_message);
        tvWifiMsg.setText(wifiMsg);

        ivRefresh.setOnClickListener(refreshClickListener);
        adapter = new WiFiListAdapter(this, R.id.tv_wifi_name, wifiAPList);
    }

    private View.OnClickListener refreshClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            startWifiScan();
        }
    };
}