package com.example.siddharth.grainprotection;

import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    static JSONObject jsonObject;
    static JSONArray jsonArray;
    private GoogleMap mMap;
    FButton buton;
    public static String Json_string="";
    public static List<String> locations,locations1,locations2,locations3;
    static String[] citynames;
    public Spinner getstate;
    public static String z1="",z2="",z3="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buton = (FButton) findViewById(R.id.buton);
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearch(view);
            }
        });
        locations=new ArrayList<>();
        locations1=new ArrayList<>();
        locations2=new ArrayList<>();
        locations3=new ArrayList<>();
        getstate=(Spinner)findViewById(R.id.getstate);
        String locationy = "London";
        citynames=new String[1000];
        BackgroundTask backgroundTask=new BackgroundTask(MapsActivity.this,"maps");
        backgroundTask.execute();


    }

    public void onSearch(View view) {

        mMap.clear();
        String state=getstate.getSelectedItem().toString().toLowerCase();

        if(state.equals("Select your state"))
        {
            Toast.makeText(MapsActivity.this,"Please select a state...!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            int co=0;
            try {
                String location=state;
                List<Address> addresslist=null;
                Geocoder geocoder=new Geocoder(this);
                try {
                    addresslist= geocoder.getFromLocationName(location,1);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address address=addresslist.get(0);

                LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng)).setTitle(""+state);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 6.0f));

                String cities="";
                for(int j=0;j<locations.size();j++)
                {
                    cities=cities+ "\n"+locations2.get(j)+" "+locations1.get(j)+"  "+locations.get(j);
                }

                if (locations != null) {
                    for(int i=0;i<locations.size();i++) {
                        Geocoder geocoder1 = new Geocoder(this);
                        try {
                            LatLng latLng1 = new LatLng(Double.parseDouble(locations1.get(i)), Double.parseDouble(locations.get(i)));
                            mMap.addMarker(new MarkerOptions().position(latLng1).snippet(""+locations3.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).setTitle(""+locations2.get(i));

                        }catch(Exception e)
                        {
                            continue;
                        }

                        co++;
                    }

                }

            }catch (Exception e)
            {
                Toast.makeText(MapsActivity.this,""+e,Toast.LENGTH_LONG).show();
            }
        }



    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //RUN TIME PERMISSION REQUEST

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE ,android.Manifest.permission.ACCESS_FINE_LOCATION,},10);

            return;
        }
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {


                String title = marker.getTitle();
                LatLng position=marker.getPosition();
                String lati=String.valueOf(position.latitude);
                String longi=String.valueOf(position.longitude);

                z1=title;
                z2=lati;
                z3=longi;

                BackgroundTask backgroundTask=new BackgroundTask(MapsActivity.this,"weather");
                backgroundTask.execute();
            }
        });


        mMap.setMyLocationEnabled(true);
    }

    public static void parseJson(String xJson)
    {

        try {
            jsonObject=new JSONObject(xJson);
            jsonArray=jsonObject.getJSONArray("server_response");
            int count=0;
            String latitudes,longitudes,citykename,data;
            while(count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);
                latitudes=jo.getString("Latitudes");
                longitudes=jo.getString("Longitudes");
                citykename=jo.getString("City_names");
                data=jo.getString("day0");

                int iend2 = data.indexOf("\n");
                String subString2="";
                if (iend2 != -1) {
                    int pos=iend2+1;
                    subString2 = data.substring(pos);
                }

                locations3.add(subString2);
                locations2.add(citykename);
                locations1.add(latitudes);
                locations.add(longitudes);
                count=count+1;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}