package com.example.siddharth.grainprotection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siddharth.grainprotection.Comman.Comman;
import com.example.siddharth.grainprotection.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class SignIn extends AppCompatActivity {
    EditText edtphone,edtPassword;
    Button btnSignIn;
    CheckBox cbkRemember;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        edtPassword=(MaterialEditText)findViewById(R.id.edtPassword);
        edtphone=(MaterialEditText)findViewById(R.id.edtphone);
        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        cbkRemember= (CheckBox) findViewById(R.id.cbkRemember);

        Paper.init(this);




        //init firebase
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog=new ProgressDialog(SignIn.this);
                mDialog.setMessage("Processing......");
                mDialog.show();;

                if(cbkRemember.isChecked())
                {
                    Paper.book().write(Comman.USER_KEY,edtphone.getText().toString());
                    Paper.book().write(Comman.PWD_KEY,edtPassword.getText().toString());
                }

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user does not present in the database
                        if (dataSnapshot.child(edtphone.getText().toString()).exists()) {

                            //get user information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtphone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                try {
                                    Toast.makeText(SignIn.this, "Sign in Successfully!!!!", Toast.LENGTH_SHORT).show();
                                    Intent ht = new Intent(SignIn.this, Homenav.class);
                                    Comman.currentUser = user;
                                    startActivity(ht);
                                }catch (Exception e)
                                {
                                     Toast.makeText(SignIn.this, ""+e, Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(SignIn.this, "WRONG PASSWORD!!!!", Toast.LENGTH_SHORT).show();
                            }
                        } else
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "User does not exist!!!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }


                });
            }
        });
    }
}
