package com.example.wallpapercarousel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class settings extends AppCompatActivity {

    Spinner spinner;
    SharedPreferences prf;
    SharedPreferences.Editor editor;
    String ratio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinner = findViewById(R.id.spinner);

        prf = getSharedPreferences("aspect", Context.MODE_PRIVATE);
        String ar = prf.getString("ratio", "9:16");

        int x;
        if(ar != null){
            switch (ar) {
                case "1:1": x = 0;
                    break;
                case "1:2": x = 1;
                    break;
                case "2:1": x = 2;
                    break;
                case "3:2": x = 3;
                    break;
                case "2:3": x = 4;
                    break;
                case "3:4": x = 5;
                    break;
                case "4:3": x = 6;
                    break;
                case "16:9": x = 7;
                    break;
                default: x = 8;
                    break;
            }
        }
        else{
            x = 8;
        }


        spinner.setSelection(x);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                prf = getSharedPreferences("aspect", Context.MODE_PRIVATE);
                editor = prf.edit();

                ratio = adapterView.getItemAtPosition(i).toString().trim();
                editor.putString("ratio", ratio);
                editor.apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void okay(View view){
        super.onBackPressed();
    }
}