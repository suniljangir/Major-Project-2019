package com.example.siddharth.grainprotection;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siddharth.grainprotection.Model.Current;
import com.example.siddharth.grainprotection.Model.Register;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MyLocation extends AppCompatActivity {
    FirebaseDatabase database,database1;
    DatabaseReference cur,cur2;
    LocationManager locationManager;
    LocationListener locationListener;
    Geocoder geocoder;
    static double x = 0, y = 0;
    TextView texting;
    Button button;
    Spinner spinner;
    String crop;
 static    String cityname="";
    final static int READ_Location = 0;
    List<Address> addresses;
    List<Current> car=new ArrayList<>();
    SharedPreferences sharedPreferences;
    final String welcomescreen = "Welcomescreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
        button=(Button)findViewById(R.id.buttoon);
        spinner=(Spinner)findViewById(R.id.cro);
        texting=(TextView)findViewById(R.id.texting) ;
        database=FirebaseDatabase.getInstance();
       cur=database.getReference("Currents");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                x = location.getLatitude();
                y = location.getLongitude();
                findaddress(x, y);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //RUN TIME PERMISSION REQUEST

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE, android.Manifest.permission.ACCESS_FINE_LOCATION,},10);

                return;
            } else {
                configurebutton();
            }
        }
    }

    private void findaddress(double x, double y) {
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            crop=spinner.getSelectedItem().toString();
            addresses = geocoder.getFromLocation(x, y, 1);
            String address = addresses.get(0).getAddressLine(0);
            String area = addresses.get(0).getLocality();
            String city = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalcode = addresses.get(0).getPostalCode();

            String fulladdress = address + " ";
            cityname=city;

            final Current current=new Current(cityname,crop,car);
            String order_number1=String.valueOf(System.currentTimeMillis());
          cur.child(order_number1).setValue(current);
           database1=FirebaseDatabase.getInstance();
            cur2=database1.getReference("");
            String input="G";
            DatabaseReference ref= cur2.child("Currents");
            cur=ref.child("Jaipur");
            cur.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> c=new ArrayList<String>();
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {
                        c.add(dataSnapshot1.getValue().toString());
                    }


                    Current current1=dataSnapshot.getValue(Current.class);
                    Gson gson=new Gson();
                    String reqjson=gson.toJson(current1);
                   texting.setText(c.toString());

                    String[] itemarray=new String[c.size()];
                    for(int i=0;i<c.size();i++)
                    {
                        itemarray[i]=c.get(i);
                    }
                     dataparsing(itemarray);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            texting.setText(" " + fulladdress+"  \n"+area);

        } catch (IOException e) {
            Toast.makeText(this, " " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void deletingdata() {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
        Query qq=ref.child("Currents").orderByChild("city").equalTo(""+cityname);
        qq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d:dataSnapshot.getChildren())
                {
                    d.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void dataparsing(String[] it)
    {
        String subString0="",subString1="",subString2="",subString3="",subString4="",subString5="",subString6="",subString7="";
        String subString01="",subString11="",subString21="",subString31="",subString41="",subString51="",subString61="",subString71="";
            texting.setText("");
        StringBuilder st=new StringBuilder();
        for(int i=0;i<it.length;i++)
        {
            st.append(""+it[i]);
            int iend1 = it[i].indexOf("=");
            if (iend1 != -1) {
                int pos = iend1 + 1;
                subString0 = it[i].substring(pos);
                subString01=it[i].substring(0,iend1);
            }
            texting.setText(texting.getText()+"\n"+it[i]);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configurebutton();
                return;
        }
    }

    private void configurebutton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager.requestLocationUpdates("gps", 50000000, 1000, locationListener);
                }
            });


        }
}
