//Team: Akshay, Sneha
package com.example.akshay.geocalc;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static android.R.attr.data;


public class homeScreen extends AppCompatActivity {
    Button settings;
    EditText p1l,p1lo,p2l,p2lo;
    //home page
    TextView dtv, btv;
    String dmeasure ="kilometer";
    String bmeasure = "degrees";
    public double dist = 0.0;
    public double bear = 0.0;
    double lat1,long1,lat2,long2;
    String s="";
    private Button Calculatedis, Clear;
    private EditText latp1, longp1, latp2, longp2;
    private TextView  distance, bearing;
    public static final int DUNITS_SELECTION = 1;
    public static final int BUNITS_SELECTION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        settings = (Button) findViewById(R.id.SettingsPressed);
        dtv = (TextView) findViewById(R.id.dmetric);
        btv = (TextView) findViewById(R.id.bmetric);

        Calculatedis = (Button) findViewById(R.id.Calculate);
        Clear = (Button) findViewById(R.id.Clear);
        latp1 = (EditText) findViewById(R.id.LP1);
        longp1 = (EditText) findViewById(R.id.LoP1);
        latp2 = (EditText) findViewById(R.id.LP2);
        longp2 = (EditText) findViewById(R.id.LoP2);
        distance = (TextView) findViewById(R.id.textView5);
        bearing = (TextView) findViewById(R.id.textView6);




        Intent intentcheck = getIntent();
        if (intentcheck.hasExtra("dselected")){
            dmeasure = getIntent().getStringExtra("dselected");

        }else
        {
            dmeasure= "Kilometers";
        }
        if (intentcheck.hasExtra("bselected")){
            bmeasure = getIntent().getStringExtra("bselected");
            updateScreen();
        }else
        {
            bmeasure= "Degrees";

        }

        if (intentcheck.hasExtra("coordindate")){
            s = getIntent().getStringExtra("coordindate");
            String[] s1 = s.split(",");
            latp1.setText(s1[0]);
            latp2.setText(s1[1]);
            longp1.setText(s1[2]);
            longp2.setText(s1[3]);


            updateScreen();
        }else
        {
            bmeasure= "Degrees";

        }

        Log.d("myTag",bmeasure + "    "+dmeasure);



        dtv.setText(dmeasure);
        btv.setText(bmeasure);





        Clear.setOnClickListener(v-> {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(Clear.getWindowToken(),0);
            latp1.setText("");
            latp2.setText("");
            longp1.setText("");
            longp2.setText("");
            distance.setText("");
            bearing.setText("");

        });

        Calculatedis.setOnClickListener(v -> {
            String l1 = latp1.getText().toString();
            String lg1 = longp1.getText().toString();
            String l2 = latp2.getText().toString();
            String lg2 = longp2.getText().toString();
            if(l1.length()==0 || lg1.length()==0 || l2.length()==0 || lg2.length()==0){
                Snackbar.make(latp1,"Please enter the values", Snackbar.LENGTH_LONG).show();
                return;}
            updateScreen();
        });
    }



    private void updateScreen()
    {
        try {
            Double lat1 = Double.parseDouble(latp1.getText().toString());
            Double lng1 = Double.parseDouble(longp1.getText().toString());
            Double lat2 = Double.parseDouble(latp2.getText().toString());
            Double lng2 = Double.parseDouble(longp2.getText().toString());


            Location p1 = new Location("");//provider name is unecessary
            p1.setLatitude(lat1);//your coords of course
            p1.setLongitude(lng1);

            Location p2 = new Location("");
            p2.setLatitude(lat2);
            p2.setLongitude(lng2);

            double b = p1.bearingTo(p2);
            double d = p1.distanceTo(p2) / 1000.0d;

            if (this.dmeasure.equals("Miles")) {
                d *= 0.621371;
            }

            if (this.bmeasure.equals("Mils")) {
                b *= 17.777777778;
            }

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);


            String dStr = "Distance: " + df.format(d) + " " + this.dmeasure;
            distance.setText(dStr);

            String bStr = "Bearing: " + df.format(b) + " " + this.bmeasure;
            bearing.setText(bStr);
            hideKeyboard();
        } catch (Exception e) {
            return;
        }

    }


    private void hideKeyboard()
    {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            //this.getSystemService(Context.INPUT_METHOD_SERVICE);
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.settings:
                Intent intent1 =new Intent(this,settingScreen.class);
                String sx1 = latp1.getText().toString();
                String sy1 = longp1.getText().toString();
                String sx2 = latp2.getText().toString();
                String sy2= longp2.getText().toString();
                String s =  sx1+","+sy1+","+sx2+","+sy2;
                intent1.putExtra("dselected", dmeasure);
                intent1.putExtra("bselected", bmeasure);
                intent1.putExtra("coordindate", s);

                this.startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
