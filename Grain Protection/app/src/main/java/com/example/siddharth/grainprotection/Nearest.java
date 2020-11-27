package com.example.siddharth.grainprotection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siddharth.grainprotection.Model.Current;
import com.example.siddharth.grainprotection.Model.Dummy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Nearest extends AppCompatActivity {
    TextView texting;
    Button button;
    FirebaseDatabase database, database1;
    DatabaseReference cur, cur2;
    List<Current> car = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest);
        database = FirebaseDatabase.getInstance();
        cur = database.getReference("Dummy");

        texting = (TextView) findViewById(R.id.texting);
        button = (Button) findViewById(R.id.buttoon);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database1 = FirebaseDatabase.getInstance();
                cur2 = database1.getReference("");
                String input = "G";
                DatabaseReference ref = cur2.child("Dummy");
                cur = ref.child("Dummy");
                cur.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> c = new ArrayList<String>();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            c.add(dataSnapshot1.getValue().toString());
                        }


                        Dummy dummy = dataSnapshot.getValue(Dummy.class);
                        Gson gson = new Gson();
                        String reqjson = gson.toJson(dummy);
                        texting.setText(c.toString());

                        String[] itemarray = new String[c.size()];
                        for (int i = 0; i < c.size(); i++) {
                            itemarray[i] = c.get(i);
                        }
                        dataparsing(itemarray);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                texting.setText("Please Wait...");

            }
        });
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
        }

    private void deletingdata() {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
        Query qq=ref.child("Dummyy").orderByChild("dummy").equalTo("");
        qq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d:dataSnapshot.getChildren())
                {
                    d.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void dataparsing(String[] it)
    {
        String subString0="",subString1="",subString2="",subString3="",subString4="",subString5="",subString6="",subString7="";
        String subString01="",subString11="",subString21="",subString31="",subString41="",subString51="",subString61="",subString71="";
        texting.setText("");
        StringBuilder st=new StringBuilder();
        for(int i=0;i<it.length;i++)
        {
            st.append(""+it[i]);

        }
        texting.setText("Nearest Locations from Current Location : Jaipur "+"\n\n"+st);


    }

    }


