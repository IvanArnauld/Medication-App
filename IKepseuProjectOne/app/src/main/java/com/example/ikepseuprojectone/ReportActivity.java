package com.example.ikepseuprojectone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ikepseuprojectone.adapters.DBAdapter;
import com.example.ikepseuprojectone.adapters.MyAdapter;
import com.example.ikepseuprojectone.models.Medication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    private String textPatName;
    private String textMedName;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewManager;
    private ArrayList<Medication> medicationArrayList;
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Find views using their IDs

        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recyclerViewManager);
        recyclerView.setHasFixedSize(true);
        medicationArrayList = new ArrayList<Medication>();

        // Database
        db = new DBAdapter(this);

        // Get existing db file, or from the assets folder if it doesn't exist
        try {
            String destPath = "data/data/" + getPackageName() + "/databases";
            File f = new File(destPath);

            if (!f.exists()) {
                f.mkdirs();

                // Copy db from assets folder
                CopyDB(getBaseContext().getAssets().open("mydb"),
                        new FileOutputStream(destPath + "/MyDB"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Open db
        db.open();

        Cursor c;
        c = db.getAllMedications();

        if (c.moveToFirst()) {
            do {
                medicationArrayList.add(new Medication(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),c.getString(4),c.getString(5)));
            } while (c.moveToNext());
        }

        // Close db
        db.close();

        recyclerViewAdapter = new MyAdapter(getApplicationContext(), medicationArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
        // Copy one byte at a time
        byte[] buffer = new byte[1024];
        int length;

        while((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        inputStream.close();
        outputStream.close();
    }

    public void onButtonReturn(View view) {
        textPatName =  medicationArrayList.get(medicationArrayList.size()-1).sPatName_.toString();
        textMedName =  medicationArrayList.get(medicationArrayList.size()-1).sMedName_.toString();
        SharedPreferences settings = getSharedPreferences("moreGui", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("patientName", textPatName);
        editor.putString("medName", textMedName);
        editor.apply(); // Writes to shared prefs

        finish(); // Go back
    }

    public void onDeleteLast(View view)
    {
        db.open();
        boolean check = db.deleteMedication(medicationArrayList.size());
        if(check)
        {
            Toast.makeText(ReportActivity.this, "Last Medication Entry Deleted", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(ReportActivity.this, "Last Medication Not Entry Deleted", Toast.LENGTH_LONG).show();
        }
        db.close();
        finish();
    }

    public void onDeleteAll(View view)
    {
        db.open();
        db.deleteAllMedications();
        Toast.makeText(ReportActivity.this, "All Medication Entries Have Been Deleted", Toast.LENGTH_LONG).show();
        db.close();
        finish();
    }
}