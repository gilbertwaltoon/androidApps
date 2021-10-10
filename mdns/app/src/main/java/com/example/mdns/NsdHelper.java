package com.example.mdns;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.net.InetAddress;

public class NsdHelper {
    Context mContext;

    NsdManager mNsdManager;
    NsdManager.ResolveListener mResolveListener;
    NsdManager.DiscoveryListener mDiscoveryListener;
    NsdManager.RegistrationListener mRegistrationListener;

    private static final String SERVICE_TYPE = "_gilbertx._tcp.";
    //private static final String SERVICE_TYPE = "_services._dns-sd._udp";

    private static final String TAG = "NSD";
   // private String mServiceName = "myappService";
   private String esp32ServiceName = "esp32";

    // First step : register a NsdServiceInfo object to advertise your service
    NsdServiceInfo mService;

    public NsdHelper(Context context) {
        mContext = context;
        mNsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
    }


    public void initializeNsd() {
       // initializeRegistrationListener();
        initializeDiscoveryListener();
        initializeResolveListener();

    }


    // Check success of service registration
    /*public void initializeRegistrationListener() {
        mRegistrationListener = new NsdManager.RegistrationListener() {

            @Override
            public void onServiceRegistered(NsdServiceInfo nsdServiceInfo) {
                mServiceName = nsdServiceInfo.getServiceName();
                Log.d(TAG, "Registered name : " + mServiceName);
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo nsdServiceInfo, int arg1) {
                Log.d(TAG, "Registration Failed");
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo nsdServiceInfo) {
                Log.d(TAG, "Unregistered");
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
            }

        };
    }

    // register any services to be advertised
    public void registerService(int port) {
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setPort(port);
        serviceInfo.setServiceName(mServiceName);
        serviceInfo.setServiceType(SERVICE_TYPE);

        mNsdManager.registerService(
                serviceInfo, NsdManager.PROTOCOL_DNS_SD, mRegistrationListener);

    }*/

    // To discover services of the type you're looking for on the network
    public void initializeDiscoveryListener() {
        mDiscoveryListener = new NsdManager.DiscoveryListener() {

            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d(TAG, "Service discovery started");
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                Log.d(TAG, "Service discovery success : " + service);
                Log.d(TAG, "Host = " + service.getServiceName());
                Log.d(TAG, "port = " + String.valueOf(service.getPort()));

                if (!service.getServiceType().equals(SERVICE_TYPE)) {
                    Log.d(TAG, "Unknown Service Type: " + service.getServiceType());
                //} //else if (service.getServiceName().equals(mServiceName)) {
                   // Log.d(TAG, "Same machine: " + mServiceName);
                };
                if (service.getServiceName().contains(esp32ServiceName)) {
                    mNsdManager.resolveService(service, mResolveListener);
                };
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                Log.e(TAG, "service lost" + service);
                if (mService == service) {
                    mService = null;
                    Log.e(TAG, "service lost" + service);
                }
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG, "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                mNsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code: " + errorCode);
                mNsdManager.stopServiceDiscovery(this);
            }
        };
    }

    public void discoverServices() {
        mNsdManager.discoverServices(
                SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);
    }


    // Connection handshake
    public void initializeResolveListener() {
        mResolveListener = new NsdManager.ResolveListener() {

            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.e(TAG, "Resolve failed" + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.d(TAG, "Resolve Succeeded. " + serviceInfo);

              /*  if (serviceInfo.getServiceName().equals(mServiceName)) {
                    Log.d(TAG, "Same IP.");
                    return;
                } */
                mService = serviceInfo;
                int port = mService.getPort();
                InetAddress host = mService.getHost();
                String address = host.getHostAddress();
                Log.d(TAG, "Resolved address = " + address);
                Log.d(TAG, "Resolved port = " + port);
            }
        };
    }

    public void stopDiscovery() {
        if (mNsdManager != null) {
            mNsdManager.stopServiceDiscovery(mDiscoveryListener);
        }
    }

    public NsdServiceInfo getChosenServiceInfo() {
        return mService;
    }

    public void tearDown() {
        if (mNsdManager != null) {
            if (mRegistrationListener != null) {
                mNsdManager.unregisterService(mRegistrationListener);
            }
            if (mDiscoveryListener != null) {
                mNsdManager.stopServiceDiscovery(mDiscoveryListener);
            }
        }
    }
}
