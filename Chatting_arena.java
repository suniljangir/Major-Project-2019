package com.secure_messaging;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

public class Chatting_arena extends AppCompatActivity {

    String senderid, receiverid;
    Intent intent;
    String msg;
    ListView chat;
    EmojiconEditText message;
    ImageView emojiImageView;
    EmojiconTextView emojiconTextView;
    Button btnsend;
    ArrayList<Chatpojo_firebase> arrayList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference reference;
    Chattingcustomadapter adapter;
    EmojIconActions emojIcon;
    ImageView sharefile;
    View rootview;
    private static int RESULT_LOAD_IMAGE = 1;
    ProgressDialog progressDialog;
    Button btnrecord;
    MediaRecorder recorder;
    String fileName = null;
    Button btn_location;
    String logindelid;
     StorageReference mStorageRef;

    private static final int GalleryPick = 1;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_chatting_arena);
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);
         intent = getIntent();
        senderid = intent.getStringExtra("loginid");
        receiverid = intent.getStringExtra("reciever_id");

        progressDialog = new ProgressDialog(this);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("chats");
        mStorageRef = FirebaseStorage.getInstance().getReference();

        btnrecord = (Button) findViewById(R.id.btn_record);
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/record_audio.3gp";//for mp3 we have to pay for decoder and encoder


        chat = (ListView) findViewById(R.id.chatting);
        message = (EmojiconEditText) findViewById(R.id.enter_message);

        btnsend = (Button) findViewById(R.id.btn_send);
        sharefile = (ImageView) findViewById(R.id.share_file);
        emojiImageView = (ImageView) findViewById(R.id.emoji_btn);

        rootview = findViewById(R.id.root_view);

        btn_location = (Button)findViewById(R.id.btn_location);

        emojIcon = new EmojIconActions(this, rootview, message, emojiImageView, "#F44336", "#e8e8e8", "#f4f4f4");
        emojIcon.ShowEmojIcon();
        emojIcon.setIconsIds(R.drawable.ic_action_keyboard, R.drawable.smiley);
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.d("1.Open = ", "Keyboard opened!");
            }

            @Override
            public void onKeyboardClose() {
                Log.d("2.close =", "Keyboard closed");
            }
        });


        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    Chatpojo_firebase pojo = data.getValue(Chatpojo_firebase.class);
                    String sid = pojo.getSenderid();
                    String rid = pojo.getReceiverid();
                    String mesg = pojo.getSendermsg();
                    String messagetype = pojo.getMessagetype();
                    if((sid.equals(senderid) && rid.equals(receiverid))||((sid.equals(receiverid) && rid.equals(senderid))))
                    arrayList.add(pojo);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d("sender id", senderid);
        adapter = new Chattingcustomadapter(this, R.layout.chatting_listview_elements, arrayList, senderid);
        chat.setAdapter(adapter);


        sharefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }


        });


        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg = message.getText().toString();
                if (msg.equals("")) {
                    Toast.makeText(getApplicationContext(), "Type something......", Toast.LENGTH_LONG).show();
                } else {
                    Chatpojo_firebase pojo = new Chatpojo_firebase();
                    logindelid = reference.child("chats").push().getKey();
                    Log.d("unique", logindelid);
                    pojo.setSenderid(senderid);
                    pojo.setReceiverid(receiverid);
                    pojo.setSendermsg(msg);
                    pojo.setLogindelid(logindelid);
                    pojo.setMessagetype("text");
                    reference.child(logindelid).setValue(pojo);
                    message.setText("");
                    message.onEditorAction(EditorInfo.IME_ACTION_DONE);


                }


            }
        });



            chat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {

                            msg = message.getText().toString();
 AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Chatting_arena.this);

                    alertBuilder.setMessage("Do you want to delete this chat")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    emojiconTextView = view.findViewById(R.id.sendermsg);
                                    msg = emojiconTextView.getText().toString();
                                    Log.d("messssss",msg+"hh");


                                    reference.addValueEventListener(new ValueEventListener() {



                                        String uniqueid;

                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            for (DataSnapshot data : dataSnapshot.getChildren()) {

                                                Chatpojo_firebase pojo = data.getValue(Chatpojo_firebase.class);
                                                String sid = pojo.getSenderid();
                                                String rid = pojo.getReceiverid();
                                                String mesg = pojo.getSendermsg();

                                                if (sid.equals(senderid) && rid.equals(receiverid) && mesg.equals(msg)) {
                                                    uniqueid = pojo.getLogindelid();
                                                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("chats").child(uniqueid);
                                                    dR.removeValue();
                                                    Toast.makeText(getApplicationContext(), "chat deleted", Toast.LENGTH_LONG).show();
                                                    break;
                                                }

                                            }



                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });



                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });


                    AlertDialog alertDialog  = alertBuilder.create();
                    alertDialog.setTitle("Delete chat");
                    alertDialog.show();
                   return false;
                }
            });

        btnrecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {  //user tapped on the button
                    startRecording();

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {   //user released the button
                    stopRecording();

                }


                return false;
            }
        });

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Uploading Location....");
                progressDialog.show();
                if (ActivityCompat.checkSelfPermission(Chatting_arena.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                client.getLastLocation().addOnSuccessListener(Chatting_arena.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null)
                        {

                            msg = "geo:"+String.valueOf(location.getLatitude())+","+String.valueOf(location.getLongitude())+"?q="+String.valueOf(location.getLatitude())+","+String.valueOf(location.getLongitude())+"(destination)";
                            Log.d("location",msg);

                            Chatpojo_firebase pojo = new Chatpojo_firebase();
                            String logindelid = reference.child("chats").push().getKey();
                            Log.d("unique", logindelid);
                            pojo.setSenderid(senderid);
                            pojo.setReceiverid(receiverid);
                            pojo.setSendermsg(msg);
                            pojo.setLogindelid(logindelid);
                            pojo.setMessagetype("location");
                            reference.child(logindelid).setValue(pojo);
                            message.setText("");
                            message.onEditorAction(EditorInfo.IME_ACTION_DONE);
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"location sent sucessfully",Toast.LENGTH_LONG).show();

                        }
                    }
                });



            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {


            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

           /* ImageView imageView = (ImageView) findViewById(R.id.imageview);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));*/

            final StorageReference storageReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(selectedImage));

            progressDialog.setMessage("Uploading image....");
            progressDialog.show();
            storageReference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(getApplicationContext(), "Sent Successful", Toast.LENGTH_LONG).show();
                    String picture = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();


                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uri1 = uri.toString();
                            Log.d("1234", uri1);
                            Chatpojo_firebase pojo = new Chatpojo_firebase();
                            String logindelid = reference.child("chats").push().getKey();
                            Log.d("unique", logindelid);
                            pojo.setSenderid(senderid);
                            pojo.setReceiverid(receiverid);
                            pojo.setSendermsg(uri1);
                            pojo.setLogindelid(logindelid);
                            pojo.setMessagetype("image");
                            reference.child(logindelid).setValue(pojo);
                            message.setText("");
                            message.onEditorAction(EditorInfo.IME_ACTION_DONE);
                            progressDialog.dismiss();
                        }
                    });


                }
            });


        }


    }


    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }


    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e("failed", "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;


        progressDialog.setMessage("Uploading audio....");
        progressDialog.show();
        final StorageReference filepath = mStorageRef.child("Audio").child(String.valueOf(System.currentTimeMillis()));
        Uri uri = Uri.fromFile(new File(fileName));

        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Audio Sent", Toast.LENGTH_LONG).show();

                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String uri1 = uri.toString();
                        Log.d("audiopath", uri1);
                        Chatpojo_firebase pojo = new Chatpojo_firebase();
                        String logindelid = reference.child("chats").push().getKey();
                        Log.d("unique", logindelid);
                        pojo.setSenderid(senderid);
                        pojo.setReceiverid(receiverid);
                        pojo.setSendermsg(uri1);
                        pojo.setLogindelid(logindelid);
                        pojo.setMessagetype("audio");
                        reference.child(logindelid).setValue(pojo);
                        message.setText("");
                        message.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        progressDialog.dismiss();
                    }
                });


            }
        });




    }
    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
    }

}
