package com.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.secure_messaging.R;

import java.util.ArrayList;

public class Registration_fragment extends Fragment {


    String number;
    String pass;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<Registerpojo_firebase> arrayList  = new ArrayList<>();
    int i=0;
    String cpass="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_registration_fragment, container, false);

        final Button registration_btnsubmit =view.findViewById(R.id.registration_btnsubmit);
        final EditText registration_number = view.findViewById(R.id.registration_number);
        final EditText registration_pass = view.findViewById(R.id.registration_pass);
        final EditText registration_confirm_pass = view.findViewById(R.id.registration_confirm_pass);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Registration");




        registration_btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 number = registration_number.getText().toString();
                 pass = registration_pass.getText().toString();
                 cpass = registration_confirm_pass.getText().toString();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data:dataSnapshot.getChildren()) {

                            Registerpojo_firebase rp = data.getValue(Registerpojo_firebase.class);

                            if (rp.getRegistration_number().equals(registration_number.getText().toString())
                                    ) {
                                Toast.makeText(getContext(), "Already Registered", Toast.LENGTH_LONG).show();
                                registration_number.setText("");
                                registration_pass.setText("");
                                registration_confirm_pass.setText("");
                                i=1;
                                break;
                            }
                        }


                         if(i != 1) {
                            if (number.equals("") || number.length() < 10) {
                                registration_number.setError("Enter valid mobile number");

                            } else if (pass.equals("")) {
                                registration_pass.setError("Enter password ");

                            } else if (cpass.equals("")) {
                                registration_confirm_pass.setError("Enter confirm password");

                            } else if (pass.equals(cpass)) {
                                String original_number = number;
                                number = "+91" + number;
                                Intent intent = new Intent(getActivity(), RegsiterOTP.class);
                                intent.putExtra("number", number);
                                intent.putExtra("original_number", original_number);
                                intent.putExtra("password", pass);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getContext(), "password and confirm password does not match", Toast.LENGTH_SHORT).show();

                            }


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Log.d("size",arrayList.size()+"");




            }
        });





        return view;
    }









}
