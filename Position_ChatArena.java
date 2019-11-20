package com.Wallpaperscreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.secure_messaging.R;

public class Position_ChatArena extends AppCompatActivity {

    EditText position;
    String pos;
    Button btnsubmit;
    Intent intent;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position__chat_arena);
        intent = getIntent();
        final String login_number=intent.getStringExtra("login_number");
        final String password=intent.getStringExtra("password");


        position = (EditText)findViewById(R.id.wallpaper_position);
        btnsubmit = (Button)findViewById(R.id.btn_submit);



        sharedPreferences = getSharedPreferences("wallpaper_position", Context.MODE_PRIVATE);

            btnsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pos = position.getText().toString();
                    if(position.getText().toString().equalsIgnoreCase("") )
                    {
                      Toast.makeText(getApplicationContext(),"Please enter the position",Toast.LENGTH_LONG).show();
                    }
                    else {
                        int posi = Integer.valueOf(pos);
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.putInt("position",posi);
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"Position is set",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Position_ChatArena.this, WallpaperScreen.class);
                        intent.putExtra("login_number",login_number);
                        intent.putExtra("password",password);
                        startActivity(intent);
                        finish();
                    }
                }
            });



    }
}
