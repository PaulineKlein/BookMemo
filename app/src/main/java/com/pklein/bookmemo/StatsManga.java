package com.pklein.bookmemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.pklein.bookmemo.data.BookContract;
import com.pklein.bookmemo.tools.BookDbTool;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StatsManga extends Fragment {
    private static final String TAG= StatsManga.class.getSimpleName();
    private int[] yData = new int[4];
    private String[] xData = new String[4];

    public static StatsManga newInstance() {
        Bundle args = new Bundle();
        StatsManga fragment = new StatsManga();
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

        getData();
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(yData[0], 0));
        entries.add(new BarEntry(yData[1], 1));
        entries.add(new BarEntry(yData[2], 2));
        entries.add(new BarEntry(yData[3], 3));

        BarDataSet dataset = new BarDataSet(entries, getResources().getString(R.string.count));

        // add colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getContext().getResources().getColor(R.color.colorGold));
        colors.add(getContext().getResources().getColor(R.color.colorBluePale));
        colors.add(getContext().getResources().getColor(R.color.colorGreen));
        colors.add(getContext().getResources().getColor(R.color.colorPink));
        dataset.setColors(colors);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add(xData[0]);
        labels.add(xData[1]);
        labels.add(xData[2]);
        labels.add(xData[3]);

        BarChart mChart = new BarChart(view.getContext());

        BarData data = new BarData(labels, dataset);
        mChart.setData(data);
        mChart.setDescription("");
        mChart.setDragEnabled(true);
        data.setValueTextColor(getContext().getResources().getColor(R.color.colorWhite));

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(10);
        l.setYEntrySpace(5);
        l.setTextColor(getContext().getResources().getColor(R.color.colorWhite));

        mChart.getAxisLeft().setTextColor(getContext().getResources().getColor(R.color.colorWhite));
        mChart.getAxisRight().setTextColor(getContext().getResources().getColor(R.color.colorWhite));
        mChart.getXAxis().setTextColor(getContext().getResources().getColor(R.color.colorWhite));

        // set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

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

        Log.i(TAG, "End onCreateView");
        return mChart;
    }

    private void getData(){
        BookDbTool bookDbTool = new BookDbTool();
        String subquery_manga_read = BookContract.BookDb.COLUMN_TYPE + "='" + BookContract.TYPE_MANGA + "'";
        String subquery_different_manga_read = BookContract.BookDb.COLUMN_TYPE + "='" + BookContract.TYPE_MANGA + "'";
        String subquery_manga_bought = BookContract.BookDb.COLUMN_TYPE + "='" + BookContract.TYPE_MANGA + "' and "+BookContract.BookDb.COLUMN_BOUGHT + "=1" ;
        String subquery_anime_seen = BookContract.BookDb.COLUMN_TYPE + "='" + BookContract.TYPE_MANGA + "' and "+BookContract.BookDb.COLUMN_EPISODE + ">0";

        String[] projection = new String[]{"count(*) AS count"};
        String[] projection2 = new String[]{"SUM("+BookContract.BookDb.COLUMN_TOME+") AS count"};
        String[] projection3 = new String[]{"SUM("+BookContract.BookDb.COLUMN_EPISODE+") AS count"};

        yData[0] = bookDbTool.getCount(subquery_different_manga_read, getActivity().getContentResolver(),projection);
        yData[1] = bookDbTool.getCount(subquery_manga_read, getActivity().getContentResolver(),projection2);
        yData[2] = bookDbTool.getCount(subquery_manga_bought, getActivity().getContentResolver(),projection2);
        yData[3] = bookDbTool.getCount(subquery_anime_seen, getActivity().getContentResolver(),projection3);

        xData[0] = getResources().getString(R.string.manga_read_diff);
        xData[1] = getResources().getString(R.string.manga_read);
        xData[2] = getResources().getString(R.string.manga_bought);
        xData[3] = getResources().getString(R.string.anime_seen);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().setContentView(R.layout.stats);
    }


}