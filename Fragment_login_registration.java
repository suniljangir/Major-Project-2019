package com.login_register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.Wallpaperscreen.WallpaperScreen;
import com.secure_messaging.R;

public class Fragment_login_registration extends FragmentActivity
         {
              private TabLayout tabLayout;
             private ViewPager viewPager;
            SharedPreferences sharedPreferences;

             @Override
             protected void onCreate(Bundle savedInstanceState) {
                 super.onCreate(savedInstanceState);
                 setContentView(R.layout.activity_fragmnet_login_registration);

                 sharedPreferences = getSharedPreferences("Userloginsession", Context.MODE_PRIVATE);

                 //Adding toolbar to the activity


                 //Initializing the tablayout
                 tabLayout = (TabLayout) findViewById(R.id.tabLayout);


                 //Adding the tabs using addTab() method
                 tabLayout.addTab(tabLayout.newTab().setText("LOGIN"));
                 tabLayout.addTab(tabLayout.newTab().setText("REGISTER"));
                 tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

                 //Initializing viewPager
                 viewPager = (ViewPager) findViewById(R.id.pager);



                 //Creating our pager adapter
                 TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager());

                 //Adding adapter to pager
                 viewPager.setAdapter(adapter);

                 //Adding onTabSelectedListener to swipe views
                 tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                     @Override
                     public void onTabSelected(TabLayout.Tab tab) {

                         viewPager.setCurrentItem(tab.getPosition());
                     }

                     @Override
                     public void onTabUnselected(TabLayout.Tab tab) {

                     }

                     @Override
                     public void onTabReselected(TabLayout.Tab tab) {

                     }
                 });


                 viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                     @Override
                     public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                         tabLayout.setScrollPosition(position, positionOffset, false);

                     }

                     @Override
                     public void onPageSelected(int position) {

                     }

                     @Override
                     public void onPageScrollStateChanged(int state) {

                     }
                 });
                    checksession();
             }
             public void checksession() {
                 int session_id_user = sharedPreferences.getInt("true", 0);
                 if (session_id_user == 1) {
                     Intent intent = new Intent(Fragment_login_registration.this, WallpaperScreen.class);
                     intent.putExtra("login_number",sharedPreferences.getString("senderid",""));
                     intent.putExtra("password",sharedPreferences.getString("password",""));

                     startActivity(intent);
                     finish();
                 }


             }
         }




