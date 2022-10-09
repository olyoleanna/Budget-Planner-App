package com.example.activitybudgetplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    CheckBox utilities, gas, rent, grocery, medical, childcare, pets, internet, credit, entertainment;

    public static String mOrderMessage = "";
    public static final String EXTRA_MESSAGE = "com.example.android.activitybudgetplanner.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utilities = findViewById(R.id.utilities_checkbox);
        gas = findViewById(R.id.gas_checkbox);
        rent = findViewById(R.id.rent_checkbox);
        grocery = findViewById(R.id.grocery_checkbox);
        medical = findViewById(R.id.medical_checkbox);
        childcare = findViewById(R.id.childcare_checkbox);
        pets = findViewById(R.id.pets_checkbox);
        internet = findViewById(R.id.internet_checkbox);
        credit = findViewById(R.id.credit_checkbox);
        entertainment = findViewById(R.id.entertainment_checkbox);

        // Get intent - bills to pay
        Intent intent = getIntent();
        String totalMessage = intent.getStringExtra("EXTRA_MESSAGE_REPORT");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Toast.makeText(getApplicationContext(), totalMessage, Toast.LENGTH_LONG).show();
        }
    }

    public void floatingButtonOnClick(View view) {
        if (!utilities.isChecked() && !gas.isChecked() && !rent.isChecked() && !grocery.isChecked()
                && !medical.isChecked() && !childcare.isChecked() && !pets.isChecked() && !internet.isChecked()
                && !credit.isChecked() && !entertainment.isChecked()) {
            Toast.makeText(getApplicationContext(), "No selection was made", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
            startActivity(intent);
            //finish();
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.utilities_checkbox:
                if (checked)
                    mOrderMessage += "Utilities:";
                else
                    mOrderMessage += "Empty:";
                break;
            case R.id.gas_checkbox:
                if (checked)
                    mOrderMessage += "Gas and Automobile:";
                else
                    mOrderMessage += "Empty:";
                break;
            case R.id.rent_checkbox:
                if (checked)
                    mOrderMessage += "Rent:";
                else
                    mOrderMessage += "Empty:";
                break;
            case R.id.grocery_checkbox:
                if (checked)
                    mOrderMessage += "Grocery:";
                else
                    mOrderMessage += "Empty:";
                break;
            case R.id.medical_checkbox:
                if (checked)
                    mOrderMessage += "Medical:";
                else
                    mOrderMessage += "Empty:";
                break;
            case R.id.childcare_checkbox:
                if (checked)
                    mOrderMessage += "Child Care:";
                else
                    mOrderMessage += "Empty:";
                break;
            case R.id.pets_checkbox:
                if (checked)
                    mOrderMessage += "Pets:";
                else
                    mOrderMessage += "Empty:";
                break;
            case R.id.internet_checkbox:
                if (checked)
                    mOrderMessage += "Internet and Data:";
                else
                    mOrderMessage += "Empty:";
                break;
            case R.id.credit_checkbox:
                if (checked)
                    mOrderMessage += "Credit Card:";
                else
                    mOrderMessage += "Empty:";
                break;
            case R.id.entertainment_checkbox:
                if (checked)
                    mOrderMessage += "Entertainment:";
                else
                    mOrderMessage += "Empty:";
                break;
        }
    }
}