package com.example.travelcompanion;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private Spinner fromSpinner;
    private Spinner toSpinner;
    private Button convertButton;
    private EditText inputValueEditText;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TabLayout tabLayout = findViewById(R.id.conversionTabLayout);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        convertButton = findViewById(R.id.convertButton);
        inputValueEditText = findViewById(R.id.inputValueEditText);
        resultTextView = findViewById(R.id.resultTextView);

        showCurrency();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                inputValueEditText.setText("");
                resultTextView.setText("");

                switch (position) {
                    case 0:
                        showCurrency();
                        break;
                    case 1:
                        showFuel();
                        break;
                    case 2:
                        showTemperature();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                convertValue(tabLayout.getSelectedTabPosition());
            }
        });
    }

    private void showCurrency() {
        setSpinnerItems(R.array.currency_array);
    }

    private void showFuel() {
        setSpinnerItems(R.array.fuel_array);
    }

    private void showTemperature() {
        setSpinnerItems(R.array.temperature_array);
    }

    private void setSpinnerItems(int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                arrayResId,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        fromSpinner.setSelection(0);
        toSpinner.setAdapter(adapter);
        toSpinner.setSelection(1);
    }

    private void convertValue(int tabPosition) {
        int fromIndex = fromSpinner.getSelectedItemPosition();
        int toIndex = toSpinner.getSelectedItemPosition();

        String input = "0";
        if (inputValueEditText.getText().length() > 0) {
            input = inputValueEditText.getText().toString();
        }

        double value = Double.parseDouble(input);

        switch (tabPosition) {
            case 0:
                convertCurrency(fromIndex, toIndex, value);
                break;
            case 1:
                convertFuel(fromIndex, toIndex, value);
                break;
            case 2:
                convertTemperature(fromIndex, toIndex, value);
                break;
        }
    }

    private void convertCurrency(int fromIndex, int toIndex, double value) {
        double[] currencyRates = {1.0, 1.55, 0.92, 148.50, 0.78};

        double usdValue = value / currencyRates[fromIndex];
        double resultValue = usdValue * currencyRates[toIndex];

        String[] currencies = getResources().getStringArray(R.array.currency_array);
        String unit = currencies[toIndex].substring(0, 3);

        resultTextView.setText(String.format("%.2f %s", resultValue, unit));
    }

    private void convertFuel(int fromIndex, int toIndex, double value) {
        double conversionRate = 0;
        double resultValue;
        String unit = "";

        if (fromIndex == toIndex) {
            resultTextView.setText(String.format("%.3f", value));
            return;
        }

        // fuel efficiency
        if ((fromIndex == 0 || fromIndex == 1) && (toIndex == 0 || toIndex == 1)) {
            conversionRate = 0.425;

            if (fromIndex == 0) {
                resultValue = value * conversionRate; // mpg to km/L
                unit = "km/L";
            } else {
                resultValue = value / conversionRate; // km/L to mpg
                unit = "mpg";
            }
        }
        // volume
        else if ((fromIndex == 2 || fromIndex == 3) && (toIndex == 2 || toIndex == 3)) {
            conversionRate = 3.785;

            if (fromIndex == 2) {
                resultValue = value * conversionRate; // gallons to litters
                unit = "Litres";
            } else {
                resultValue = value / conversionRate; // liters to gallons
                unit = "Gallons";
            }
        }
        // distance
        else if ((fromIndex == 4 || fromIndex == 5) && (toIndex == 4 || toIndex == 5)) {
            conversionRate = 1.852;

            if (fromIndex == 4) {
                resultValue = value * conversionRate; // miles to kilometres
                unit = "km";
            } else {
                resultValue = value / conversionRate; // kilometres to miles
                unit = "m";
            }
        }
        // invalid
        else {
            resultTextView.setText("Invalid fuel conversion");
            return;
        }

        resultTextView.setText(String.format("%.3f %s", resultValue, unit));
    }

    private void convertTemperature(int fromIndex, int toIndex, double value) {
        double resultValue = 0;
        String unit = "";

        if (fromIndex == toIndex) {
            resultTextView.setText(String.format("%.2f", value));
            return;
        }

        switch (fromIndex) {
            case 0: // from celcius
            {
                // c to f
                if (toIndex == 1) {
                    resultValue = (value * 1.8) + 32;
                    unit = "F";
                }
                // c to k
                else if (toIndex == 2) {
                    resultValue = value + 273.15;
                    unit = "K";
                }
                break;
            }
            // from fahrenheit
            case 1:
            {
                // f to c
                if (toIndex == 0) {
                    resultValue = (value - 32) / 1.8;
                    unit = "C";
                }
                // f to k
                else if (toIndex == 2) {
                    double celsius = (value - 32) / 1.8;
                    resultValue = celsius + 273.15;
                    unit = "K";
                }
                break;
            }
            // from kelvin
            case 2:
            {
                // k to c
                if (toIndex == 0) {
                    resultValue = value - 273.15;
                    unit = "C";
                }
                // k to f
                else if (toIndex == 1) {
                    double celsius = value - 273.15;
                    resultValue = (celsius * 1.8) + 32;
                    unit = "F";
                }
                break;
            }
        }

        resultTextView.setText(String.format("%.2f %s", resultValue, unit));
    }
}