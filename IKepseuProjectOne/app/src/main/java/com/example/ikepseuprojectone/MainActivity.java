package com.example.ikepseuprojectone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ikepseuprojectone.adapters.DBAdapter;
import com.example.ikepseuprojectone.R;
import com.example.ikepseuprojectone.adapters.MyAdapter;
import com.example.ikepseuprojectone.models.Medication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatePickerDialog datePickerDialog;
    Button timeButton, dateButton;
    private EditText editPatientName, editMedName;
    private String medType;
    private Spinner typeSpinner;
    int hour, minute;

    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeSpinner = findViewById(R.id.medTypeSpinner);
        typeSpinner.setOnItemSelectedListener(this);
        editPatientName = findViewById(R.id.editTextName);
        editMedName = findViewById(R.id.editTextMed);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodayDate());
        timeButton = findViewById(R.id.timeButton);
        medType = typeSpinner.getSelectedItem().toString();

        // Database
        db = new DBAdapter(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Find the SharedPreferences using the key
        SharedPreferences settings = getSharedPreferences("moreGui", Context.MODE_PRIVATE);

        // Set the value to the textview, if it can't find it it will use the default value provided
        editPatientName.setText(settings.getString("patientName", ""));
        editMedName.setText(settings.getString("medName", ""));

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        medType = typeSpinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private String getTodayDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);

    }
    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };
        // int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, /*style,*/ onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void onAddRecordClick(View view) {
        db.open();
        String patName = editPatientName.getText().toString();
        String medName = editMedName.getText().toString();
        String date = dateButton.getText().toString();
        String time = timeButton.getText().toString();

        long check = db.insertMedication(patName,medName, medType, date, time);

        if(check != -1) {
            CharSequence toastText = editMedName.getText().toString() + " Medication for " + editPatientName.getText().toString() + " set for " + dateButton.getText().toString() + " at " + timeButton.getText().toString();
            Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(MainActivity.this, "Medication Reminder not recorded", Toast.LENGTH_LONG).show();
        }
        db.close();
    }


    public void onReportClick(View view) {
        // Define your intent and the Activity to navigate to
        Intent intent = new Intent(this, ReportActivity.class);

        // (Optional) Add any extras


        // Start the new Activity
        startActivity(intent);
    }

    public void onSettingsClick(View view) {
        // Define your intent and the Activity to navigate to
        Intent intent = new Intent(this, SettingsActivity.class);

        // (Optional) Add any extras


        // Start the new Activity
        startActivity(intent);
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}