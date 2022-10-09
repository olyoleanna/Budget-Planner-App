package com.example.activitybudgetplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    String SEPERATOR = ":";

    // for report
    public static String answerFinal = ""; // pass one single string of edittext inputs to report
    public static String answerCheck = ""; // for checking
    public static final String EXTRA_MESSAGE = "com.example.android.activitybudgetplanner.extra.MESSAGE";

    // for textview to report
    public static String dataText = ""; // pass one single string of edittext inputs to report
    //public static final String EXTRA_MESSAGE_DATA_TEXT = "com.example.android.activitybudgetplanner.extra.MESSAGE";

    List<EditText> alleditTexts = new ArrayList<EditText>();

    EditText editText;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ScrollView scrollView = new ScrollView(this);
        setContentView(scrollView);

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        Button reportButton = new Button(this);
        editText = null;

        linearLayout.removeAllViews();

        // Get intent - bills to pay
        Intent intent = getIntent();
        String[] datas = intent.getStringExtra(MainActivity.EXTRA_MESSAGE).split(SEPERATOR);

        // INSTRUCTION OR TITLE
        TextView textView = new TextView(this);
        textView.setText("Enter amount per bill type");
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(20);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        linearLayout.addView(textView);

        // GENERATE EDIT TEXTS FROM SELECTED ITEMS IN MAIN
        if (datas.length > 0) {
            for (int i = 0; i < datas.length; i++) {
                if (!datas[i].equals("Empty")) { // if not unchecked
                    editText = new EditText(this);

                    editText.setTextSize(20);
                    editText.setHint(datas[i] + ": $");

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(60, 20, 60, 20);

                    editText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL); // number decimal

                    linearLayout.addView(editText, layoutParams);

                    alleditTexts.add(editText); // push edit texts to array
                }
                else {
                    linearLayout.removeView(editText);

                    alleditTexts.remove(editText);
                }
            }
        }
        else {
            return;
        }

        String[] amountsResult = new String[alleditTexts.size()]; // convert list to string array if edittext inputs

        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(32, 20, 32, 32);
        layoutParams2.gravity = Gravity.BOTTOM | Gravity.END;

        reportButton.setLayoutParams(layoutParams2);
        reportButton.setText("Report");
        reportButton.setTextColor(Color.WHITE);
        reportButton.setBackgroundColor(0xFF6200EE); // default purple color

        Toast.makeText(getApplicationContext(), "Kindly fill up all text boxes", Toast.LENGTH_LONG).show();

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i < alleditTexts.size(); i++){
                    amountsResult[i] = alleditTexts.get(i).getText().toString();
                    answerFinal += amountsResult[i] + ":"; // to be separated in report activity before use
                    answerCheck += amountsResult[i]; // for checking
                    dataText += datas[i] + ":"; // to be separated in report activity before use
                }

                //if (answerCheck.isEmpty()) { // if no input
                    //Toast.makeText(getApplicationContext(), "Textbox inputs incomplete", Toast.LENGTH_LONG).show();
                //}
                //else {
                    Intent intent1 = new Intent(SecondActivity.this, ReportActivity.class);
                    intent1.putExtra(EXTRA_MESSAGE, answerFinal); // pass float inputs for chart
                    intent1.putExtra("EXTRA_MESSAGE_DATA_TEXT", dataText); // pass separated selected items from main to report
                    startActivity(intent1);
                    finish();
                //}
            }
        });

        linearLayout.addView(reportButton, layoutParams2);
        scrollView.addView(linearLayout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        linearLayout.removeAllViews();
    }
}