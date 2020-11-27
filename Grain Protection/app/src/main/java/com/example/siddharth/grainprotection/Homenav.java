package com.example.siddharth.grainprotection;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siddharth.grainprotection.Comman.Comman;
import com.example.siddharth.grainprotection.Model.Location;
import com.example.siddharth.grainprotection.Model.Register;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import info.hoang8f.widget.FButton;
import io.paperdb.Paper;
import me.relex.circleindicator.CircleIndicator;

import static com.example.siddharth.grainprotection.R.id.edtphone1;
import static com.example.siddharth.grainprotection.R.id.name;
import static com.example.siddharth.grainprotection.R.id.phone1;

public class Homenav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase database;
    DatabaseReference requests, registers, loca;
    static ViewPager mViewPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.r1,R.drawable.r2,R.drawable.r3,R.drawable.r4,R.drawable.r5};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    TextView t1, textfullView;
    CustomSwipeAdapter adapter;
    FirebaseStorage storage;
    StorageReference storageRefrence;
    Button bt1, bt2, bt3;

    List<Request> cart = new ArrayList<>();
    List<Register> car = new ArrayList<>();
    List<Location> lo = new ArrayList<>();

    MaterialEditText edtphone, edtlocation, edtcomment;
    EditText date, city;
    FButton btnSelect, btnUpload;
    Uri saveuri;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homenav);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Paper.init(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog1();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);



        View headerView = navigationView.getHeaderView(0);
        textfullView = (TextView) headerView.findViewById(R.id.textFullName);
        textfullView.setText(Comman.currentUser.getName());

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        registers = database.getReference("Registers");
        loca = database.getReference("Locati");
        storage = FirebaseStorage.getInstance();
        storageRefrence = storage.getReference();
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homenav.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homenav.this, CropsDisplay.class);
                startActivity(intent);
            }
        });

    }


    private void showAlertDialog1() {
        AlertDialog.Builder alertDialog11 = new AlertDialog.Builder(Homenav.this);
        alertDialog11.setTitle("नया उपयोगकर्ता पंजीकृत करें/ ");
        alertDialog11.setMessage("कृपया पूरी जानकारी भरें");
        final MaterialEditText name, phone1, location;
        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.add_register, null);
        alertDialog11.setView(add_menu_layout);
        phone1 = (MaterialEditText) findViewById(R.id.phone1);
        name = (MaterialEditText) findViewById(R.id.name);
        location = (MaterialEditText) findViewById(R.id.Location);
        alertDialog11.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               /* String xxx=name.getText().toString();
                 Register register=new Register(
                         xxx,
                        phone1.getText().toString(),
                         location.getText().toString(),
                         car);
                String order_number1=String.valueOf(System.currentTimeMillis());
                registers.child(order_number1).setValue(register);*/

                Toast.makeText(Homenav.this, "New User Registerd  :D", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
        alertDialog11.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog11.show();

    }

    private void showAlertDialog() {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Homenav.this);
        alertDialog.setTitle("Add New Comment");
        alertDialog.setMessage("Please Fill Full information");
        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.add_comment, null);
        edtphone = (MaterialEditText) add_menu_layout.findViewById(edtphone1);
        edtlocation = (MaterialEditText) add_menu_layout.findViewById(R.id.edtLocation);
        edtcomment = (MaterialEditText) add_menu_layout.findViewById(R.id.edtComment);
        btnSelect = (FButton) add_menu_layout.findViewById(R.id.btnSelect);
        btnUpload = (FButton) add_menu_layout.findViewById(R.id.btnUpload);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();// select image from the gallery;
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
                Request request = new Request(

                        edtphone.getText().toString(),
                        edtcomment.getText().toString(),
                        edtlocation.getText().toString(),

                        cart

                );

                //submit to firebase
                //in this we use System.CurrentMilli to key
                String order_number = String.valueOf(System.currentTimeMillis());
                requests.child(order_number).setValue(request);
                Toast.makeText(Homenav.this, "Your comment has been sent  :D", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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

    private void uploadImage() {
        if (saveuri != null) {
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("UPLOADING!!!!!!!");
            pd.show();
            ;


            String imageNmae = UUID.randomUUID().toString();
            final StorageReference imageFolder = storageRefrence.child("image/" + imageNmae);
            imageFolder.putFile(saveuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    ;
                    Toast.makeText(Homenav.this, "Image uploaded!! ;D", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Homenav.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    pd.setMessage("uploded" + progress + "%");

                }
            });

        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            saveuri = data.getData();
            btnSelect.setText("Image Selected!");
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Select Pitcure"), PICK_IMAGE_REQUEST);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homenav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.about) {
            Intent intent = new Intent(Homenav.this, About.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {

        }
        if (id == R.id.nav_msp) {

            Intent inte=new Intent(Homenav.this,Msp.class);
            startActivity(inte);


        } else if (id == R.id.nav_call) {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Homenav.this);
            alertDialog.setTitle("Call");
            alertDialog.setMessage("Kisan Call Center : 18001801551");
            alertDialog.setPositiveButton("CALL"
                    , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "18001801551"));
                    if (ActivityCompat.checkSelfPermission(Homenav.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(intent);
                }
            });

           alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
               }
           });
            alertDialog.show();


            } else if (id == R.id.nav_sell) {

            Intent inten=new Intent(Homenav.this,sell.class);
            startActivity(inten);

        }
        else if(id==R.id.nav_nearest)
        {
           Intent intent=new Intent(Homenav.this,Nearest.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_logout) {
            Paper.book().destroy();

            Intent intent=new Intent(Homenav.this,SignIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void init() {
        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new ImageAdapter(Homenav.this,XMENArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }


}
