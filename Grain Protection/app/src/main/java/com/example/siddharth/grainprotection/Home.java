package com.example.siddharth.grainprotection;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siddharth.grainprotection.Model.Request;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import info.hoang8f.widget.FButton;


import static com.example.siddharth.grainprotection.R.id.edtphone1;

public class Home extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference requests;
    ViewPager viewPager;
    TextView t1;
    CustomSwipeAdapter adapter;
    FirebaseStorage storage;
    StorageReference storageRefrence;
    Button bt1,bt2,bt3;

    List<Request> cart=new ArrayList<>();
    MaterialEditText edtphone,edtlocation,edtcomment;
    FButton btnSelect,btnUpload;
    Uri saveuri;
    private final int PICK_IMAGE_REQUEST=71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager= (ViewPager) findViewById(R.id.view_pager);
        bt1= (Button) findViewById(R.id.bt1);
        bt2= (Button) findViewById(R.id.bt2);
        bt3= (Button) findViewById(R.id.bt3);

        adapter=new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);

        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");
        storage=FirebaseStorage.getInstance();
        storageRefrence=storage.getReference();
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,MapsActivity.class);
                startActivity(intent);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Home.this);
        alertDialog.setTitle("Add New Comment");
        alertDialog.setMessage("Please Fill Full information");
        LayoutInflater inflater=this.getLayoutInflater();
        View add_menu_layout=inflater.inflate(R.layout.add_comment,null);
        edtphone= (MaterialEditText) add_menu_layout.findViewById(edtphone1);
        edtlocation= (MaterialEditText) add_menu_layout.findViewById(R.id.edtLocation);
        edtcomment= (MaterialEditText) add_menu_layout.findViewById(R.id.edtComment);
        btnSelect= (FButton) add_menu_layout.findViewById(R.id.btnSelect);
        btnUpload= (FButton) add_menu_layout.findViewById(R.id.btnUpload);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();//letUs select image from the gallery;
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });





        alertDialog.setView(add_menu_layout);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request=new Request(

                       edtphone.getText().toString(),
                        edtcomment.getText().toString(),
                        edtlocation.getText().toString(),

                        cart

                );

                //submit to firebase
                //in this we use System.CurrentMilli to key
                String order_number=String.valueOf(System.currentTimeMillis());
                requests.child(order_number).setValue(request);
                Toast.makeText(Home.this, "Your comment has been sent  :D", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();




    }

    private void uploadImage()
    {
        if(saveuri!=null)
        {
            final ProgressDialog pd=new ProgressDialog(this);
            pd.setMessage("UPLOADING!!!!!!!");
            pd.show();;


            String imageNmae= UUID.randomUUID().toString();
            final StorageReference imageFolder=storageRefrence.child("image/"+imageNmae);
            imageFolder.putFile(saveuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();;
                    Toast.makeText(Home.this, "Image uploaded!! ;D", Toast.LENGTH_SHORT).show();
                    imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {


                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(Home.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100*taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    pd.setMessage("uploded"+progress+"%");

                }
            });

        }


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null &&data.getData()!=null)
        {
            saveuri=data.getData();
            btnSelect.setText("Image Selected!");
        }
    }

    private void chooseImage() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Select Pitcure"),PICK_IMAGE_REQUEST);

    }
}
