package com.example.siddharth.grainprotection;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class weatesting extends AppCompatActivity {

    public static String locationname="",locationlat="",locationlong="";
    public static String loc="",lat="",long1="";

    TextView locationame,date0,temp,desc;
    TextView date1,temp1,desc1;
    TextView date2,temp2,desc2;
    TextView date3,temp3,desc3;
    TextView date4,temp4,desc4;
    TextView date5,temp5,desc5;
    TextView date6,temp6,desc6;
    TextView date7,temp7,desc7;
    static JSONObject jsonObject;
    static JSONArray jsonArray;
    public static boolean checker=false;
    static String t0,t1,t2,t3,t4,t5,t6,t7;
    static String dd0,dd1,dd2,dd3,dd4,dd5,dd6,dd7;

    public static int i1=0,i2=0,i3=0,i4=0,i5=0,i6=0,i7=0,i8=0,i9=0,i10=0;
    public static int b1=0,b2=0,b3=0,b4=0,b5=0,b6=0,b7=0,b8=0,b9=0,b10=0;
    public static int c1=0,c2=0,c3=0,c4=0,c5=0,c6=0,c7=0,c8=0,c9=0,c10=0;
    public static int d1=0,d2=0,d3=0,d4=0,d5=0,d6=0,d7=0,d8=0,d9=0,d10=0;
    public static int e1=0,e2=0,e3=0,e4=0,e5=0,e6=0,e7=0,e8=0,e9=0,e10=0;
    public static int f1=0,f2=0,f3=0,f4=0,f5=0,f6=0,f7=0,f8=0,f9=0,f10=0;
    public static int g1=0,g2=0,g3=0,g4=0,g5=0,g6=0,g7=0,g8=0,g9=0,g10=0;
    public static int h1=0,h2=0,h3=0,h4=0,h5=0,h6=0,h7=0,h8=0,h9=0,h10=0;

    ImageView imageView,imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatesting);

        locationame=(TextView)findViewById(R.id.locationname);
        date0=(TextView)findViewById(R.id.date);
        imageView=(ImageView)findViewById(R.id.imageView);
        temp=(TextView)findViewById(R.id.temp);
        desc=(TextView)findViewById(R.id.desc);

        date1=(TextView)findViewById(R.id.date1);
        imageView1=(ImageView)findViewById(R.id.imageView1);
        temp1=(TextView)findViewById(R.id.temp1);
        desc1=(TextView)findViewById(R.id.desc1);

        date2=(TextView)findViewById(R.id.date2);
        imageView2=(ImageView)findViewById(R.id.imageView2);
        temp2=(TextView)findViewById(R.id.temp2);
        desc2=(TextView)findViewById(R.id.desc2);


        date3=(TextView)findViewById(R.id.date3);
        imageView3=(ImageView)findViewById(R.id.imageView3);
        temp3=(TextView)findViewById(R.id.temp3);
        desc3=(TextView)findViewById(R.id.desc3);

        date4=(TextView)findViewById(R.id.date4);
        imageView4=(ImageView)findViewById(R.id.imageView4);
        temp4=(TextView)findViewById(R.id.temp4);
        desc4=(TextView)findViewById(R.id.desc4);

        date5=(TextView)findViewById(R.id.date5);
        imageView5=(ImageView)findViewById(R.id.imageView5);
        temp5=(TextView)findViewById(R.id.temp5);
        desc5=(TextView)findViewById(R.id.desc5);

        date6=(TextView)findViewById(R.id.date6);
        imageView6=(ImageView)findViewById(R.id.imageView6);
        temp6=(TextView)findViewById(R.id.temp6);
        desc6=(TextView)findViewById(R.id.desc6);

        date7=(TextView)findViewById(R.id.date7);
        imageView7=(ImageView)findViewById(R.id.imageView7);
        temp7=(TextView)findViewById(R.id.temp7);
        desc7=(TextView)findViewById(R.id.desc7);

        loc=MapsActivity.z1;
        lat=MapsActivity.z2;
        long1=MapsActivity.z3;

        locationame.setText(loc);

        DateFormat dateFormat = new SimpleDateFormat("EEE,d MMM yyyy");

        TimeZone tz = TimeZone.getTimeZone("GMT+05:30");
        Calendar c = Calendar.getInstance(tz);
        String time = String.format("%02d" , c.get(Calendar.HOUR))+":"+
                String.format("%02d" , c.get(Calendar.MINUTE));

        final String date = dateFormat.format(Calendar.getInstance().getTime());

        date0.setText("Today  "+date);

        Calendar cal = Calendar.getInstance(tz);
        Date yourDate=Calendar.getInstance(tz).getTime();
        cal.setTime(yourDate);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        yourDate = cal.getTime();
        final String dat1 = dateFormat.format(yourDate);
        date1.setText("Tom "+dat1);

        cal.add(Calendar.DAY_OF_MONTH, 1);
        yourDate = cal.getTime();
        final String dat2 = dateFormat.format(yourDate);
        date2.setText(dat2);

        cal.add(Calendar.DAY_OF_MONTH, 1);
        yourDate = cal.getTime();
        final String dat3 = dateFormat.format(yourDate);
        date3.setText(dat3);

        cal.add(Calendar.DAY_OF_MONTH, 1);
        yourDate = cal.getTime();
        final String dat4 = dateFormat.format(yourDate);
        date4.setText(dat4);


        cal.add(Calendar.DAY_OF_MONTH, 1);
        yourDate = cal.getTime();
        final String dat5 = dateFormat.format(yourDate);
        date5.setText(dat5);

        cal.add(Calendar.DAY_OF_MONTH, 1);
        yourDate = cal.getTime();
        final String dat6 = dateFormat.format(yourDate);
        date6.setText(dat6);

        cal.add(Calendar.DAY_OF_MONTH, 1);
        yourDate = cal.getTime();
        final String dat7 = dateFormat.format(yourDate);
        date7.setText(dat7);



        if(checker)
        {
            temp.setText(t0);
            temp1.setText(t1);
            temp2.setText(t2);
            temp3.setText(t3);
            temp4.setText(t4);
            temp5.setText(t5);
            temp6.setText(t6);
            temp7.setText(t7);

            desc.setText(dd0);
            desc1.setText(dd1);
            desc2.setText(dd2);
            desc3.setText(dd3);
            desc4.setText(dd4);
            desc5.setText(dd5);
            desc6.setText(dd6);
            desc7.setText(dd7);
        }
        else
        {
            Toast.makeText(weatesting.this,"Please Retry after some time...!",Toast.LENGTH_LONG).show();
        }

        try {

            if (i1 == 1) {
                imageView.setImageResource(R.drawable.rain2);
            }
            if (i2 == 1) {
                imageView.setImageResource(R.drawable.rain2);
            }
            if (i3 == 1) {
                imageView.setImageResource(R.drawable.partlysunny);
            }
            if (i4 == 1) {
                imageView.setImageResource(R.drawable.mostlysunny);
            }
            if (i5 == 1) {
                imageView.setImageResource(R.drawable.sunny);
            }
            if (i6 == 1) {
                imageView.setImageResource(R.drawable.hazy);
            }
            if (i7 == 1) {
                imageView.setImageResource(R.drawable.thunderstorm);
            }
            if (i8 == 1) {
                imageView.setImageResource(R.drawable.hailstorm);
            }
            if (i9 == 1) {
                imageView.setImageResource(R.drawable.sunny);
            }
            if (i10 == 1) {
                imageView.setImageResource(R.drawable.cloud);
            }
            if (b1 == 1) {
                imageView1.setImageResource(R.drawable.rain2);
            }
            if (b2 == 1) {
                imageView1.setImageResource(R.drawable.rain2);
            }
            if (b3 == 1) {
                imageView1.setImageResource(R.drawable.partlysunny);
            }
            if (b4 == 1) {
                imageView1.setImageResource(R.drawable.mostlysunny);
            }
            if (b5 == 1) {
                imageView1.setImageResource(R.drawable.sunny);
            }
            if (b6 == 1) {
                imageView1.setImageResource(R.drawable.hazy);
            }
            if (b7 == 1) {
                imageView1.setImageResource(R.drawable.thunderstorm);
            }
            if (b8 == 1) {
                imageView1.setImageResource(R.drawable.hailstorm);
            }
            if (b9 == 1) {
                imageView1.setImageResource(R.drawable.sunny);
            }
            if (b10 == 1) {
                imageView1.setImageResource(R.drawable.cloud);
            }

            if (c1 == 1) {
                imageView2.setImageResource(R.drawable.rain2);
            }
            if (c2 == 1) {
                imageView2.setImageResource(R.drawable.rain2);
            }
            if (c3 == 1) {
                imageView2.setImageResource(R.drawable.partlysunny);
            }
            if (c4 == 1) {
                imageView2.setImageResource(R.drawable.mostlysunny);
            }
            if (c5 == 1) {
                imageView2.setImageResource(R.drawable.sunny);
            }
            if (c6 == 1) {
                imageView2.setImageResource(R.drawable.hazy);
            }
            if (c7 == 1) {
                imageView2.setImageResource(R.drawable.thunderstorm);
            }
            if (c8 == 1) {
                imageView2.setImageResource(R.drawable.hailstorm);
            }

            if (c9 == 1) {
                imageView2.setImageResource(R.drawable.sunny);
            }
            if (c10 == 1) {
                imageView2.setImageResource(R.drawable.cloud);
            }

            if (d1 == 1) {
                imageView3.setImageResource(R.drawable.rain2);
            }
            if (d2 == 1) {
                imageView3.setImageResource(R.drawable.rain2);
            }
            if (d3 == 1) {
                imageView3.setImageResource(R.drawable.partlysunny);
            }
            if (d4 == 1) {
                imageView3.setImageResource(R.drawable.mostlysunny);
            }
            if (d5 == 1) {
                imageView3.setImageResource(R.drawable.sunny);
            }
            if (d6 == 1) {
                imageView3.setImageResource(R.drawable.hazy);
            }
            if (d7 == 1) {
                imageView3.setImageResource(R.drawable.thunderstorm);
            }
            if (d8 == 1) {
                imageView3.setImageResource(R.drawable.hailstorm);
            }

            if (d9 == 1) {
                imageView3.setImageResource(R.drawable.sunny);
            }
            if (d10 == 1) {
                imageView3.setImageResource(R.drawable.cloud);
            }

            if (e1 == 1) {
                imageView4.setImageResource(R.drawable.rain2);
            }
            if (e2 == 1) {
                imageView4.setImageResource(R.drawable.rain2);
            }
            if (e3 == 1) {
                imageView4.setImageResource(R.drawable.partlysunny);
            }
            if (e4 == 1) {
                imageView4.setImageResource(R.drawable.mostlysunny);
            }
            if (e5 == 1) {
                imageView4.setImageResource(R.drawable.sunny);
            }
            if (e6 == 1) {
                imageView4.setImageResource(R.drawable.hazy);
            }
            if (e7 == 1) {
                imageView4.setImageResource(R.drawable.thunderstorm);
            }
            if (e8 == 1) {
                imageView4.setImageResource(R.drawable.hailstorm);
            }
            if (e9 == 1) {
                imageView4.setImageResource(R.drawable.sunny);
            }
            if (e10 == 1) {
                imageView4.setImageResource(R.drawable.cloud);
            }
            if (f1 == 1) {
                imageView5.setImageResource(R.drawable.rain2);
            }
            if (f2 == 1) {
                imageView5.setImageResource(R.drawable.rain2);
            }
            if (f3 == 1) {
                imageView5.setImageResource(R.drawable.partlysunny);
            }
            if (f4 == 1) {
                imageView5.setImageResource(R.drawable.mostlysunny);
            }
            if (f5 == 1) {
                imageView5.setImageResource(R.drawable.sunny);
            }
            if (f6 == 1) {
                imageView5.setImageResource(R.drawable.hazy);
            }
            if (f7 == 1) {
                imageView5.setImageResource(R.drawable.thunderstorm);
            }
            if (f8 == 1) {
                imageView5.setImageResource(R.drawable.hailstorm);
            }

            if (f9 == 1) {
                imageView5.setImageResource(R.drawable.sunny);
            }
            if (f10 == 1) {
                imageView5.setImageResource(R.drawable.cloud);
            }

            if (g1 == 1) {
                imageView6.setImageResource(R.drawable.rain2);
            }
            if (g2 == 1) {
                imageView6.setImageResource(R.drawable.rain2);
            }
            if (g3 == 1) {
                imageView6.setImageResource(R.drawable.partlysunny);
            }
            if (g4 == 1) {
                imageView6.setImageResource(R.drawable.mostlysunny);
            }
            if (g5 == 1) {
                imageView6.setImageResource(R.drawable.sunny);
            }
            if (g6 == 1) {
                imageView6.setImageResource(R.drawable.hazy);
            }
            if (g7 == 1) {
                imageView6.setImageResource(R.drawable.thunderstorm);
            }
            if (g8 == 1) {
                imageView6.setImageResource(R.drawable.hailstorm);
            }
            if (g9 == 1) {
                imageView6.setImageResource(R.drawable.sunny);
            }
            if (g10 == 1) {
                imageView6.setImageResource(R.drawable.cloud);
            }

            if (h1 == 1) {
                imageView7.setImageResource(R.drawable.rain2);
            }
            if (h2 == 1) {
                imageView7.setImageResource(R.drawable.rain2);
            }
            if (h3 == 1) {
                imageView7.setImageResource(R.drawable.partlysunny);
            }
            if (h4 == 1) {
                imageView7.setImageResource(R.drawable.mostlysunny);
            }
            if (h5 == 1) {
                imageView7.setImageResource(R.drawable.sunny);
            }
            if (h6 == 1) {
                imageView7.setImageResource(R.drawable.hazy);
            }
            if (h7 == 1) {
                imageView7.setImageResource(R.drawable.thunderstorm);
            }
            if (h8 == 1) {
                imageView7.setImageResource(R.drawable.hailstorm);
            }
            if (h9 == 1) {
                imageView7.setImageResource(R.drawable.sunny);
            }
            if (h10 == 1) {
                imageView7.setImageResource(R.drawable.cloud);
            }


        }catch (Exception e)
        {
            Toast.makeText(weatesting.this,""+e,Toast.LENGTH_LONG).show();
        }



    }

    public static void parsingJson(String result)
    {
        checker=true;
        try {
            jsonObject=new JSONObject(result);
            jsonArray=jsonObject.getJSONArray("server_response");
            int count=0;
            String descing0,descing2,descing3,descing4,descing5,descing6,descing7,descing1;
            while(count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);

                descing0=jo.getString("day0");
                descing1=jo.getString("day1");
                descing2=jo.getString("day2");
                descing3=jo.getString("day3");
                descing4=jo.getString("day4");
                descing5=jo.getString("day5");
                descing6=jo.getString("day6");
                descing7=jo.getString("day7");



                int iend1 = descing0.indexOf("\n");
                int iend2 = descing1.indexOf("\n");
                int iend3 = descing2.indexOf("\n");
                int iend4 = descing3.indexOf("\n");
                int iend5 = descing4.indexOf("\n");
                int iend6 = descing5.indexOf("\n");
                int iend7 = descing6.indexOf("\n");
                int iend8 = descing7.indexOf("\n");

                String subString0="",subString1="",subString2="",subString3="",subString4="",subString5="",subString6="",subString7="";
                String subString01="",subString11="",subString21="",subString31="",subString41="",subString51="",subString61="",subString71="";
                if (iend1 != -1) {
                    int pos = iend1 + 1;
                    subString0 = descing0.substring(pos);
                    subString01=descing0.substring(0,iend1);
                }
                if (iend2 != -1) {
                    int pos = iend2 + 1;
                    subString1 = descing1.substring(pos);
                    subString11=descing1.substring(0,iend2);
                }
                if (iend3 != -1) {
                    int pos = iend2 + 1;
                    subString2 = descing2.substring(pos);
                    subString21=descing2.substring(0,iend3);

                }
                if (iend4 != -1) {
                    int pos = iend2 + 1;
                    subString3 = descing3.substring(pos);
                    subString31=descing3.substring(0,iend4);

                }
                if (iend5 != -1) {
                    int pos = iend2 + 1;
                    subString4 = descing4.substring(pos);
                    subString41=descing4.substring(0,iend5);

                }
                if (iend6 != -1) {
                    int pos = iend2 + 1;
                    subString5 = descing5.substring(pos);
                    subString51=descing5.substring(0,iend6);
                }
                if (iend7 != -1) {
                    int pos = iend2 + 1;
                    subString6 = descing6.substring(pos);
                    subString61=descing6.substring(0,iend7);
                }
                if (iend8 != -1) {
                    int pos = iend2 + 1;
                    subString7 = descing7.substring(pos);
                    subString71=descing7.substring(0,iend8);
                }

                dd0=subString0;
                dd1=subString1;
                dd2=subString2;
                dd3=subString3;
                dd4=subString4;
                dd5=subString5;
                dd6=subString6;
                dd7=subString7;

                t0=subString01;
                t1=subString11;
                t2=subString21;
                t3=subString31;
                t4=subString41;
                t5=subString51;
                t6=subString61;
                t7=subString71;

                String x1="shower";
                String x2="rain";

                String x3="Partly sunny";
                String x4="Mostly sunny";

                String x5="Sunny";

                String x6="Hazy sun";
                String x7="thunderstorm";

                String x8="hailstorm";

                String x9="sunshine";
                String x10="Mostly cloudy";
                if(dd0.contains(""+x1))
                {
                    i1=1;
                }
                if(dd0.contains(""+x2))
                {
                    i2=1;
                }
                if(dd0.contains(""+x3))
                {
                    i3=1;
                }
                if(dd0.contains(""+x4))
                {
                    i4=1;
                }
                if(dd0.contains(""+x5))
                {
                    i5=1;
                }
                if(dd0.contains(""+x6))
                {
                    i6=1;
                }
                if(dd0.contains(""+x7))
                {
                    i7=1;
                }
                if(dd0.contains(""+x8))
                {
                    i8=1;
                }
                if(dd0.contains(""+x9))
                {
                    i9=1;
                }
                if(dd0.contains(""+x10))
                {
                    i10=1;
                }

                if(dd1.contains(""+x1))
                {
                    b1=1;
                }
                if(dd1.contains(""+x2))
                {
                    b2=1;
                }
                if(dd1.contains(""+x3))
                {
                    b3=1;
                }
                if(dd1.contains(""+x4))
                {
                    b4=1;
                }
                if(dd1.contains(""+x5))
                {
                    b5=1;
                }
                if(dd1.contains(""+x6))
                {
                    b6=1;
                }
                if(dd1.contains(""+x7))
                {
                    b7=1;
                }
                if(dd1.contains(""+x8))
                {
                    b8=1;
                }
                if(dd1.contains(""+x9))
                {
                    b9=1;
                }
                if(dd1.contains(""+x10))
                {
                    b10=1;
                }

                if(dd2.contains(""+x1))
                {
                    c1=1;
                }
                if(dd2.contains(""+x2))
                {
                    c2=1;
                }
                if(dd2.contains(""+x3))
                {
                    c3=1;
                }
                if(dd2.contains(""+x4))
                {
                    c4=1;
                }
                if(dd2.contains(""+x5))
                {
                    c5=1;
                }
                if(dd2.contains(""+x6))
                {
                    c6=1;
                }
                if(dd2.contains(""+x7))
                {
                    c7=1;
                }
                if(dd2.contains(""+x8))
                {
                    c8=1;
                }

                if(dd2.contains(""+x9))
                {
                    c9=1;
                }
                if(dd2.contains(""+x10))
                {
                    c10=1;
                }

                if(dd3.contains(""+x1))
                {
                    d1=1;
                }
                if(dd3.contains(""+x2))
                {
                    d2=1;
                }
                if(dd3.contains(""+x3))
                {
                    d3=1;
                }
                if(dd3.contains(""+x4))
                {
                    d4=1;
                }
                if(dd3.contains(""+x5))
                {
                    d5=1;
                }
                if(dd3.contains(""+x6))
                {
                    d6=1;
                }
                if(dd3.contains(""+x7))
                {
                    d7=1;
                }
                if(dd3.contains(""+x8))
                {
                    d8=1;
                }
                if(dd3.contains(""+x9))
                {
                    d9=1;
                }
                if(dd3.contains(""+x10))
                {
                    d10=1;
                }




                if(dd4.contains(""+x1))
                {
                    e1=1;
                }
                if(dd4.contains(""+x2))
                {
                    e2=1;
                }
                if(dd4.contains(""+x3))
                {
                    e3=1;
                }
                if(dd4.contains(""+x4))
                {
                    e4=1;
                }
                if(dd4.contains(""+x5))
                {
                    e5=1;
                }
                if(dd4.contains(""+x6))
                {
                    e6=1;
                }
                if(dd4.contains(""+x7))
                {
                    e7=1;
                }
                if(dd4.contains(""+x8))
                {
                    e8=1;
                }
                if(dd4.contains(""+x9))
                {
                    e9=1;
                }
                if(dd4.contains(""+x10))
                {
                    e10=1;
                }

                if(dd5.contains(""+x1))
                {
                    f1=1;
                }
                if(dd5.contains(""+x2))
                {
                    f2=1;
                }
                if(dd5.contains(""+x3))
                {
                    f3=1;
                }
                if(dd5.contains(""+x4))
                {
                    f4=1;
                }
                if(dd5.contains(""+x5))
                {
                    f5=1;
                }
                if(dd5.contains(""+x6))
                {
                    f6=1;
                }
                if(dd5.contains(""+x7))
                {
                    f7=1;
                }
                if(dd5.contains(""+x8))
                {
                    f8=1;
                }
                if(dd5.contains(""+x9))
                {
                    f9=1;
                }
                if(dd5.contains(""+x10))
                {
                    f10=1;
                }


                if(dd6.contains(""+x1))
                {
                    g1=1;
                }
                if(dd6.contains(""+x2))
                {
                    g2=1;
                }
                if(dd6.contains(""+x3))
                {
                    g3=1;
                }
                if(dd6.contains(""+x4))
                {
                    g4=1;
                }
                if(dd6.contains(""+x5))
                {
                    g5=1;
                }
                if(dd6.contains(""+x6))
                {
                    g6=1;
                }
                if(dd6.contains(""+x7))
                {
                    g7=1;
                }
                if(dd6.contains(""+x8))
                {
                    g8=1;
                }

                if(dd6.contains(""+x9))
                {
                    g9=1;
                }
                if(dd6.contains(""+x10))
                {
                    g10=1;
                }

                if(dd7.contains(""+x1))
                {
                    h1=1;
                }
                if(dd7.contains(""+x2))
                {
                    h2=1;
                }
                if(dd7.contains(""+x3))
                {
                    h3=1;
                }
                if(dd7.contains(""+x4))
                {
                    h4=1;
                }
                if(dd7.contains(""+x5))
                {
                    h5=1;
                }
                if(dd7.contains(""+x6))
                {
                    h6=1;
                }
                if(dd7.contains(""+x7))
                {
                    h7=1;
                }
                if(dd7.contains(""+x8))
                {
                    h8=1;
                }
                if(dd7.contains(""+x9))
                {
                    h9=1;
                }
                if(dd7.contains(""+x10))
                {
                    h10=1;
                }

                count=count+1;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
