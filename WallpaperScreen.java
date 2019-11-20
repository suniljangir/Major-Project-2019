package com.Wallpaperscreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.secure_messaging.Enter_id;
import com.secure_messaging.R;

import java.util.ArrayList;

public class WallpaperScreen extends AppCompatActivity {

    ListView listView;
    ImageView security;
    Intent intent;
    SharedPreferences sharedPreferences;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_screen);


        security =(ImageView)findViewById(R.id.security);
        listView = (ListView)findViewById(R.id.wallpaper_listview);

        intent = getIntent();
        final String login_number=intent.getStringExtra("login_number");
        final String password=intent.getStringExtra("password");

        sharedPreferences = getSharedPreferences("UserValidation", Context.MODE_PRIVATE);


        final SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("login_number",login_number);
        editor.putString("password",password);
        editor.commit();





        ArrayList<Integer> image=new ArrayList<>();
        image.add(R.drawable.eclair);
        image.add(R.drawable.froyo);
        image.add(R.drawable.gingerbread);
        image.add(R.drawable.honeycomb);


        ImageListAdapter imageListAdapteradapter = new ImageListAdapter(WallpaperScreen.this, R.layout.listview_elements,image);
        listView.setAdapter(imageListAdapteradapter);



        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WallpaperScreen.this,Position_validation.class);
                intent.putExtra("login_number",login_number);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               /* Intent intent = new Intent(getApplicationContext(),Fragment_login_registration.class);
                startActivity(intent);*/

                sharedPreferences = getSharedPreferences("wallpaper_position", Context.MODE_PRIVATE);
                pos = sharedPreferences.getInt("position",0);
                editor.commit();

                if(i ==  pos)
               {
                   intent = new Intent(getApplicationContext(), Enter_id.class);
                   intent.putExtra("login_number",login_number);
                   intent.putExtra("password",password);
                   startActivity(intent);
                   Toast.makeText(getBaseContext(),"item one clicked",Toast.LENGTH_LONG).show();
               }
                else
                {
                    Toast.makeText(getBaseContext(),"good choice",Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        System.exit(0);
    }
}
