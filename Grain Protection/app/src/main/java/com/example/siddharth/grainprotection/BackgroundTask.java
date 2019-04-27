package com.example.siddharth.grainprotection;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Siddarth Jain on 12/19/2017.
 */

class BackgroundTask extends AsyncTask<String,Void,String> {

    Context ctxx;
    String json_url,json_url2;
    String Json;
    static String type="";
    ProgressDialog progressDialog;
    public static String loc2="",lat2="",long2="";
    BackgroundTask(Context ctx,String s) {
        this.ctxx = ctx;
        type=s;
        if(type.equals("weather"))
        {
            loc2=MapsActivity.z1;
            lat2=MapsActivity.z2;
            long2=MapsActivity.z3;
        }
    }

    @Override
    protected void onPreExecute() {
        json_url = "http://siddarthjainit19.000webhostapp.com/maps.php";
        json_url2 = "http://siddarthjainit19.000webhostapp.com/weatherinfo.php";
        progressDialog=new ProgressDialog(ctxx);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... args) {
        if(type.equals("maps")) {
            try {

                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String result = "", line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            if (type.equals("weather")) {
                try {

                    URL url = new URL(json_url2);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                     httpURLConnection.setRequestMethod("POST");
                     httpURLConnection.setDoOutput(true);

                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                    String data_string = URLEncoder.encode("loc2", "UTF-8") + "=" + URLEncoder.encode(loc2, "UTF-8") + "&" +
                            URLEncoder.encode("lat2", "UTF-8") + "=" + URLEncoder.encode(lat2, "UTF-8") + "&" +
                            URLEncoder.encode("long2", "UTF-8") + "=" + URLEncoder.encode(long2, "UTF-8") ;
                    bufferedWriter.write(data_string);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();



                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    String result = "", line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
            return null;

        }


        @Override
        protected void onProgressUpdate (Void...values){
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String x){
            if(type.equals("maps"))
            {
                Json=x;
                MapsActivity.parseJson(x);
            }

            if(type.equals("weather"))
            {
                weatesting.parsingJson(x);
                Intent intent=new Intent(ctxx,weatesting.class);
                ctxx.startActivity(intent);
            }


            progressDialog.dismiss();
        }




}


