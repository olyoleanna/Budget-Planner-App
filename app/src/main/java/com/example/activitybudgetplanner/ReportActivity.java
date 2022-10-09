package com.example.activitybudgetplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReportActivity extends AppCompatActivity {

    PieChart piechart;

    ArrayList<Integer> colors;

    String[] inputs;
    String[] data_texts;
    String SEPERATOR = ":";

    TextView totalTextView;
    float finalTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        piechart = findViewById(R.id.pieChartid);
        totalTextView = findViewById(R.id.totalTextView);

        // GET ARRAY OF FLOAT INTENT
        Intent intent = getIntent();
        inputs = intent.getStringExtra(SecondActivity.EXTRA_MESSAGE).split(SEPERATOR);

        // get data text
        data_texts = intent.getStringExtra("EXTRA_MESSAGE_DATA_TEXT").split(SEPERATOR);

        for (int i = 0; i < inputs.length; i++) {
            finalTotal += Float.parseFloat(inputs[i]);
        }

        totalTextView.setText("Total: $" + String.format("%.2f", finalTotal));

        piechart.getDescription().setText("");
        piechart.setRotationEnabled(true);
        piechart.setHoleRadius(30f);
        piechart.setTransparentCircleAlpha(0);

        Legend legend = piechart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        addDataSet();

        if (savedInstanceState != null) { // to show text result or make it visible
            boolean isVisible = savedInstanceState.getBoolean("reply_visible");
            if (isVisible) {
                totalTextView.setVisibility(View.VISIBLE);
                totalTextView.setText(savedInstanceState.getString("reply_text"));
            }

        }
    }

    private void addDataSet() {
        ArrayList<PieEntry> numValues; numValues = new ArrayList<>();

        for (int i = 0; i < inputs.length; i++) {
            numValues.add(new PieEntry(Float.parseFloat(inputs[i]), data_texts[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(numValues, "");
        pieDataSet.setSliceSpace(3);
        pieDataSet.setValueTextSize(15);

        colors = new ArrayList<>();

        for (int i = 0; i < inputs.length; i++) {
            Random rand = new Random();
            int col = rand.nextInt(128);

            colors.add(Color.argb(255, rand.nextInt(256 - col) + col, rand.nextInt(256 - col) + col, rand.nextInt(256 - col) + col));

        }

        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        piechart.setData(pieData);
        piechart.invalidate(); // refreshes chart by adding created values, previously empty
    }

    public void backtoMainOnClick(View view) {
        Intent intent0 = new Intent(ReportActivity.this, MainActivity.class);
        intent0.putExtra("EXTRA_MESSAGE_REPORT", "Total calculated budget: $" + String.format("%.2f", finalTotal));
        startActivity(intent0);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) { // save output before layout change or before destroyed
        super.onSaveInstanceState(outState);
        if (totalTextView.getVisibility() == View.VISIBLE) {
            outState.putBoolean("reply_visible", true);
            outState.putString("reply_text", totalTextView.getText().toString());
        }
    }
}