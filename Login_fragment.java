package com.login_register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Wallpaperscreen.WallpaperScreen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.secure_messaging.R;

/**
 * Created by Kshitij on 9/20/2018.
 */

public class Login_fragment extends Fragment {
    int i=0;
    FirebaseDatabase database;
    TextView forgot_password;
    SharedPreferences sharedPreferences;
     String login;
     String pass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("Registration");


        sharedPreferences =  getActivity().getSharedPreferences("Userloginsession", Context.MODE_PRIVATE);

        View view = inflater.inflate(R.layout.activity_login_fragment, container, false);

        Button btn_login = view.findViewById(R.id.btn_login);
        final EditText loginid = view.findViewById(R.id.loginid);
        final EditText password = view.findViewById(R.id.password);




        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  login = loginid.getText().toString();
                pass  = password.getText().toString();

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            Registerpojo_firebase rp = data.getValue(Registerpojo_firebase.class);
                            String number_pojo = rp.getRegistration_number();
                            String pass_pojo = rp.getPassword();


                            if (login == null || pass == null)
                            {
                                Toast.makeText(getContext(), "Enter credentials", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (number_pojo.equals(login)) {
                                    if (pass_pojo.equals(pass)) {
                                        i=1;
                                        break;
                                    }
                                }

                            }
                        }


                        if (i == 1)
                        {
                            loginsession();
                            Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(),WallpaperScreen.class);
                         //   Intent intent = new Intent(getContext(),Enter_id.class);
                            intent.putExtra("login_number",login);
                            intent.putExtra("password",pass);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
                        }
                        loginid.setText("");
                        password.setText("");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




            }
        });



        return view;
    }
    public void loginsession()
    {

        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("senderid",login);
        editor.putString("password",pass);
        editor.putInt("true",1);
        editor.commit();



    }
}
