package com.example.loadingdialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

class LoadingDialog {

   private Activity activity;
   private AlertDialog dialog;

// constructor
    LoadingDialog(Activity myActivity){
        activity = myActivity;
    }

    // we made this
    void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // use the activity to get a layout inflater
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog, null));
        builder.setCancelable(false);

        dialog = builder.create();;
        dialog.show();
    }

     // we made this
    void dismissDialog() {
        dialog.dismiss();
    }
}
