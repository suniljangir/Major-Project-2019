package com.login_register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.secure_messaging.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegsiterOTP extends AppCompatActivity {
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseAuth firebaseAuth;
    String verificationid;

     FirebaseDatabase database;
     DatabaseReference reference;
     StorageReference storageReference;

    EditText register_otp;
    CircleImageView registration_photo;
    String number,password,original_number;
    Button btn_submit;
    String registration_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsiter_otp);
        Intent intent = getIntent();
        number=intent.getStringExtra("number");
        password=intent.getStringExtra("password");
        original_number=intent.getStringExtra("original_number");

        register_otp =(EditText)findViewById(R.id.edit_otp);
        btn_submit =(Button)findViewById(R.id.btn_submit);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Registration");
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile_Images");
         registration_id = reference.push().getKey();



        sendVerificationCode(number);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code= register_otp.getText().toString();

                if(code.equalsIgnoreCase("") || code.length()<6)
                {
                    register_otp.setError("Enter code");
                    register_otp.findFocus();
                    return; //it will stop the further execution
                }
                verifycode(code);
            }
        });


    }
   private void chooseImage() {

    // Method to choose image

        Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                registration_photo.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }





    private  void sendVerificationCode(String number)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(

                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallback
        );
    }

    //for automatic verification
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationid =s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();

            if(code != null)
            {
                //code detected automatically
                register_otp.setText(code);
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };


    private void verifycode(String code)
    {
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationid,code);
        signInWithCredential(phoneAuthCredential);
    }

    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                           // uploadImage();
                            userregister(original_number,password);
                            Intent intent = new Intent(RegsiterOTP.this, Fragment_login_registration.class);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }





    /*private void uploadImage() {

    method to upload an image

        if(filePath != null)
        {

            StorageReference ref = storageReference.child(filePath.getLastPathSegment());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String imageurl = taskSnapshot.getStorage().getDownloadUrl().toString();
                            userregister(imageurl,number,password);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(RegsiterOTP.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                        }
                    });
        }
    }*/

    private void userregister( String original_number, String password) {
        Registerpojo_firebase rp = new Registerpojo_firebase();

        //set the names
        rp.setRegistration_id(registration_id);
        rp.setRegistration_number(original_number);
        rp.setPassword(password);

       /* rp.setImageurl(imageuri);*/

        reference.push().setValue(rp);
        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(RegsiterOTP.this, Fragment_login_registration.class);
        startActivity(intent);

    }
}





