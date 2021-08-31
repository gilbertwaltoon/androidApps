package com.example.mpgraph1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // https://www.youtube.com/watch?v=yrbgN2UvKGQ
    LineChart mpLineChart;
    int mycolorArray[] = {R.color.mycolour1, R.color.mycolour2, R.color.mycolour3, R.color.mycolour4};
    int[] colorClassArray = new int[]{Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA};
    String[] legendName = {"Cow", "Dog", "Cat", "Bat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mpLineChart = (LineChart) findViewById(R.id.line_chart);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "Data Set 1");
        LineDataSet lineDataSet2 = new LineDataSet(dataValues2(), "Data Set 2");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);

//        mpLineChart.setBackgroundColor(Color.GREEN);
        mpLineChart.setNoDataText("No Data");
        mpLineChart.setNoDataTextColor(Color.BLUE);

        mpLineChart.setDrawGridBackground(true);
        mpLineChart.setDrawBorders(true);
        mpLineChart.setBorderColor(Color.RED);
        //      mpLineChart.setBorderWidth(5);

        /** LINE **/
        lineDataSet1.setLineWidth(4);
        lineDataSet1.setColor(Color.RED);
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setDrawCircleHole(false);
        lineDataSet1.setCircleColor(Color.GRAY);
        lineDataSet1.setCircleHoleColor(Color.GREEN);
        lineDataSet1.setCircleRadius(10);
        lineDataSet1.setCircleHoleRadius(9);
        lineDataSet1.setValueTextSize(10);
        lineDataSet1.setValueTextColor(Color.BLUE);
        lineDataSet1.enableDashedLine(5, 10, 0);
        lineDataSet1.setColors(mycolorArray, MainActivity.this);
        lineDataSet1.setValueFormatter(new MyValeFormatter());


        /** LEGEND **/
        Legend legend = mpLineChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(Color.RED);
        legend.setTextSize(15);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormSize(10);
        legend.setXEntrySpace(5);
        legend.setFormToTextSpace(10);
        LegendEntry[] legendEntries = new LegendEntry[4];
        for (int i = 0; i < legendEntries.length; i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colorClassArray[i];
            entry.label = String.valueOf(legendName[i]);
            legendEntries[i] = entry;
        }
        legend.setCustom(legendEntries);

        /** DESCRIPTION  **/
        Description description = new Description();
        description.setText("myTxt");
        description.setTextColor(Color.MAGENTA);
        description.setTextSize(20);
        mpLineChart.setDescription(description);

        /** PLOT **/
        LineData data = new LineData(dataSets);

        mpLineChart.setData(data);
        mpLineChart.invalidate();
    }

    private ArrayList<Entry> dataValues1() {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 20));
        dataVals.add(new Entry(1, 24));
        dataVals.add(new Entry(2, 2));
        dataVals.add(new Entry(3, 10));
        dataVals.add(new Entry(4, 28));
        return dataVals;
    }

    private ArrayList<Entry> dataValues2() {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 12));
        dataVals.add(new Entry(2, 16));
        dataVals.add(new Entry(3, 23));
        dataVals.add(new Entry(5, 1));
        dataVals.add(new Entry(7, 18));
        return dataVals;
    }

    /* Add pound sign to values */
    private class MyValeFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return value + " £";
        }
    }
}