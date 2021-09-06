package com.example.mdns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.net.InetAddress;


public class MainActivity extends AppCompatActivity {

     private NsdManager mNsdManager;
     private NsdManager.DiscoveryListener mDiscoveryListener;
     private NsdManager.ResolveListener mResolveListener;
     private NsdServiceInfo mServiceInfo;
     public String mRPiAddress;
    // The NSD service type that the RPi exposes.
      private static final String SERVICE_TYPE = "_workstation._tcp.";
    //private static final String SERVICE_TYPE = "_services._dns-sd._udp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRPiAddress = "";
        mNsdManager = (NsdManager) (getApplicationContext().getSystemService(Context.NSD_SERVICE));
        initializeResolveListener();
        initializeDiscoveryListener();
        mNsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
    }

    private void initializeDiscoveryListener() {

        // Instantiate a new DiscoveryListener
        mDiscoveryListener = new NsdManager.DiscoveryListener() {

            //  Called as soon as service discovery begins.
            @Override
            public void onDiscoveryStarted(String regType) {
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                Toast.makeText(getApplicationContext(), "Found",
                        Toast.LENGTH_LONG).show();
                // A service was found!  Do something with it.
                String name = service.getServiceName();
                String type = service.getServiceType();
                Log.d("NSD", "Service Name=" + name);
                Log.d("NSD", "Service Type=" + type);
            if (type.equals(SERVICE_TYPE) && name.contains(RASPEBERTU_PI_NAME_IN_QUOTES)) {
                    Log.d("NSD", "Service Found @ '" + name + "'");
                    mNsdManager.resolveService(service, mResolveListener);
                }
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                // When the network service is no longer available.
                // Internal bookkeeping code goes here.
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                mNsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                mNsdManager.stopServiceDiscovery(this);
            }
        };
    }

    private void initializeResolveListener() {
        mResolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                // Called when the resolve fails.  Use the error code to debug.
                Log.e("NSD", "Resolve failed" + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Toast.makeText(getApplicationContext(), "onservice resolved",
                        Toast.LENGTH_SHORT).show();

                mServiceInfo = serviceInfo;

                // Port is being returned as 9. Not needed.
                //int port = mServiceInfo.getPort();

                InetAddress host = mServiceInfo.getHost();
                String address = host.getHostAddress();
                Log.d("NSD", "Resolved address = " + address);
                mRPiAddress = address;
            }
        };
    }
}