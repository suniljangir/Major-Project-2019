package com.secure_messaging;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.login_register.Fragment_login_registration;
import com.login_register.Registerpojo_firebase;

public class Enter_id extends AppCompatActivity {
    EditText editText_number;
    Button btn_enter,contacts;
    Intent intent;
    SharedPreferences sharedPreferences;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button btn_logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_id);
        editText_number =(EditText) findViewById(R.id.number);
        btn_enter =(Button)findViewById(R.id.btn_enter);
        contacts = (Button)findViewById(R.id.btn_contacts);
        btn_logout = (Button)findViewById(R.id.btn_logout);
        intent = getIntent();
         String reciever_id = editText_number.getText().toString();
        final String loginid = intent.getStringExtra("login_number");
        sharedPreferences = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Registration");

        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("login_number",loginid);
        editor.commit();

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),DisplayContacts.class);
                startActivity(intent);
            }
        });


        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String reciever_id = editText_number.getText().toString();

                if(loginid.equals(reciever_id))
                {
                    Toast.makeText(getApplicationContext(),"You have Entered Your own number",Toast.LENGTH_LONG).show();
                }
                else {


                    reference.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int i = 0;

                            for (DataSnapshot data : dataSnapshot.getChildren()) {

                                Registerpojo_firebase rp = data.getValue(Registerpojo_firebase.class);
                                String number_pojo = rp.getRegistration_number();


                                if (reciever_id.equals(number_pojo)) {
                                    i = 1;
                                    break;
                                }

                            }

                            if (i == 1) {
                                Intent intent = new Intent(getApplicationContext(), Chatting_arena.class);
                                intent.putExtra("loginid", loginid);
                                intent.putExtra("reciever_id", reciever_id);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_LONG).show();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               SharedPreferences sharedPreferences1 =  getSharedPreferences("Userloginsession", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                editor1.clear();
                editor1.commit();
                intent = new Intent(Enter_id.this, Fragment_login_registration.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
