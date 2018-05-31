package com.example.akshay.geocalc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import java.util.ArrayList;
import java.util.List;


public class settingScreen extends AppCompatActivity {

    ImageView imgClick;
    Spinner dspinner,bspinner;
    String ddrop, bdrop, s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);
        this.setTitle("Settings");

        Intent intentcheck = getIntent();
        if (intentcheck.hasExtra("dselected")){
            ddrop = getIntent().getStringExtra("dselected");
        }else
        {
            ddrop= "Kilometers";
        }
        if (intentcheck.hasExtra("bselected")){
            bdrop = getIntent().getStringExtra("bselected");
        }else
        {
            bdrop= "Degrees";
        }
        if (intentcheck.hasExtra("coordindate")){

            s=getIntent().getStringExtra("coordindate");

        } else
        {
            s ="";
        }


        String[] s11 = getResources().getStringArray(R.array.Distance_units);
        imgClick = (ImageView)findViewById(R.id.imageView4);
        dspinner = (Spinner) findViewById(R.id.spinner1);
        bspinner = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> dspinner_Adapter = new ArrayAdapter<String>(settingScreen.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Distance_units));
        dspinner_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dspinner.setAdapter(dspinner_Adapter);

        for ( int i = 0 ; i  < dspinner.getCount() ; i++ ){
            if(dspinner.getItemAtPosition(i).toString().compareTo(ddrop)==0) {
                dspinner.setSelection(i);
            }
        }

        ArrayAdapter<String> bspinner_Adapter = new ArrayAdapter<String>(settingScreen.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Bearing_units));
        bspinner_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bspinner.setAdapter(bspinner_Adapter);

        for ( int i = 0 ; i  < bspinner.getCount() ; i++ ){
            if(bspinner.getItemAtPosition(i).toString().compareTo(bdrop)==0) {
                bspinner.setSelection(i);
            }
        }

        imgClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1;
                intent1 = new Intent(getApplicationContext(),homeScreen.class);
                String dselected = dspinner.getSelectedItem().toString();
                String bselected = bspinner.getSelectedItem().toString();
                intent1.putExtra("dselected", dselected);
                intent1.putExtra("bselected", bselected);
                intent1.putExtra("coordindate", s);
                startActivity(intent1);
            }
        });
    }
}
