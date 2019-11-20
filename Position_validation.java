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

public class Position_validation extends AppCompatActivity {


    EditText passkey;
    Button btnsubmit;
    String password;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_validation);

        Intent intent = getIntent();
        final String login_number=intent.getStringExtra("login_number");
        final String password1=intent.getStringExtra("password");

        passkey = (EditText) findViewById(R.id.passkey);
        btnsubmit =(Button)findViewById(R.id.btn_submit);




        sharedPreferences = getSharedPreferences("UserValidation", Context.MODE_PRIVATE);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String confirm_pass = sharedPreferences.getString("password","");
                password = passkey.getText().toString();
                if(confirm_pass.equals(password)) {
                    Intent intent = new Intent(Position_validation.this, Position_ChatArena.class);
                    intent.putExtra("login_number",login_number);
                    intent.putExtra("password",password1);
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"u entered "+password,Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}
