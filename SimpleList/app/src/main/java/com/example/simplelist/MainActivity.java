package com.example.simplelist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list = (ListView) findViewById(R.id.the_list);
        Log.d(TAG, "on create started");

        ArrayList<String> flowers = new ArrayList<>();
        flowers.add("daisy");
        flowers.add("dandelion");
        flowers.add("rose");
        flowers.add("spurge");
        flowers.add("daffodil");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, flowers);
        list.setAdapter(adapter);

    }
}
