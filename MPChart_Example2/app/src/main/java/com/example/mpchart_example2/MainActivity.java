package com.example.mpchart_example2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, LineChartActivity.class);
     //   EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
     //   String message = editText.getText().toString();
      //  intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}