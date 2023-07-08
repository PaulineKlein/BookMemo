package com.pklein.bookmemo;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.pklein.bookmemo.data.BookContract;
import com.pklein.bookmemo.tools.BookDbTool;

import java.util.ArrayList;
import butterknife.ButterKnife;


public class StatsGlobal extends Fragment {
    private static final String TAG= StatsGlobal.class.getSimpleName();

    private PieChart mPieChart;
    private int[] yData = new int[3];
    private String[] xData = new String[3];

    public static StatsGlobal newInstance() {
        Bundle args = new Bundle();
        StatsGlobal fragment = new StatsGlobal();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "Start onCreateView");
        final View view = inflater.inflate(R.layout.stats, container, false);
        ButterKnife.bind(this, view);

        // HELP of https://www.ssaurel.com/blog/learn-to-create-a-pie-chart-in-android-with-mpandroidchart/
        mPieChart = new PieChart(view.getContext());
        mPieChart.setUsePercentValues(true);
        mPieChart.setDrawHoleEnabled(false);
        mPieChart.setDescription("");

        // enable rotation :
        mPieChart.setRotationAngle(0);
        mPieChart.setRotationEnabled(true);

        // set a chart value selected listener
        mPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;

                Toast.makeText(view.getContext(),xData[e.getXIndex()] + " : " + Math.round(e.getVal()), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected() { }
        });

        // add data
        getData();
        addData();

        // customize legends
        Legend l = mPieChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(10);
        l.setYEntrySpace(5);
        l.setTextColor(getContext().getResources().getColor(R.color.colorWhite));

        Log.i(TAG, "End onCreateView");
        return mPieChart;
    }

    private void getData(){
        BookDbTool bookDbTool = new BookDbTool();
        String subquery_lit = BookContract.BookDb.COLUMN_TYPE + "='" + BookContract.TYPE_LITERATURE + "'";
        String subquery_manga = BookContract.BookDb.COLUMN_TYPE + "='" + BookContract.TYPE_MANGA + "'";
        String subquery_comic = BookContract.BookDb.COLUMN_TYPE + "='" + BookContract.TYPE_COMIC + "'";
        String[] projection = new String[]{"count(*) AS count"};

        yData[0] = bookDbTool.getCount(subquery_lit, getActivity().getContentResolver(),projection);
        yData[1] = bookDbTool.getCount(subquery_manga, getActivity().getContentResolver(),projection);
        yData[2] = bookDbTool.getCount(subquery_comic, getActivity().getContentResolver(),projection);

        xData[0]= getActivity().getApplicationContext().getResources().getString(R.string.TAB_literature);
        xData[1]= getActivity().getApplicationContext().getResources().getString(R.string.TAB_manga);
        xData[2]= getActivity().getApplicationContext().getResources().getString(R.string.TAB_comic);

    }

    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getContext().getResources().getColor(R.color.colorGold));
        colors.add(getContext().getResources().getColor(R.color.colorBluePale));
        colors.add(getContext().getResources().getColor(R.color.colorGreen));
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        mPieChart.invalidate();
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().setContentView(R.layout.stats);
    }
}