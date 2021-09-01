package com.example.mpchartsql;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


// https://www.youtube.com/watch?v=clcD3wa9JP8&list=PLFh8wpMiEi89LcBupeftmAcgDKCeC24bJ&index=16

public class MainActivity extends AppCompatActivity {

    LineChart lineChart;
    EditText xEditText, yEditText;
    Button btnInsert, btnShow;
    MyHelper myhelper;
    SQLiteDatabase sqLiteDatabase;
    LineDataSet lineDataSet= new LineDataSet(null, null);
    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    LineData lineData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineChart = findViewById(R.id.mpChart);
        xEditText = findViewById(R.id.editTextX);
        yEditText = findViewById(R.id.editTextY);
        btnInsert = findViewById(R.id.btnInsert);
        btnShow = findViewById(R.id.btnShow);
        myhelper = new MyHelper(this);
        sqLiteDatabase = myhelper.getWritableDatabase();
        exqInsertBtn();
        exqShowBtn();

        lineDataSet.setLineWidth(4);
    }

    private void exqShowBtn() {
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lineDataSet.setValues(getDataValues());
                lineDataSet.setLabel("DataSet 1");
                dataSets.clear();
                dataSets.add(lineDataSet);
                lineData = new LineData(dataSets);
                lineChart.clear();
                lineChart.setData(lineData);
                lineChart.invalidate();

            }
        });
}
        private void exqInsertBtn() {
            btnInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    float xVal = Float.parseFloat(String.valueOf(xEditText.getText()));
                    float yVal = Float.parseFloat(String.valueOf(yEditText.getText()));
                    myhelper.insertData(xVal, yVal);
                }
            });
        }


        private ArrayList<Entry> getDataValues()
        {
            ArrayList<Entry> dataVals = new ArrayList<>();
            String[] columns = {"xValues","yValues"};
            Cursor cursor = sqLiteDatabase.query("myTable", columns, null, null, null, null, null);
            for (int i=0; i<cursor.getCount(); i++){
                cursor.moveToNext();
                dataVals.add(new Entry(cursor.getFloat(0), cursor.getFloat(1)));
            }
            cursor.close();
            return dataVals;
        }
    }