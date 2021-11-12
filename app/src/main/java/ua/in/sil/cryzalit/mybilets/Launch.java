package ua.in.sil.cryzalit.mybilets;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class Launch extends AppCompatActivity {
    AnimationDrawable fireAnimation;
    String number;
    Thread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finishAffinity();
            System.exit(0);
            Launch.this.finish();
            if(Build.VERSION.SDK_INT>=16 && Build.VERSION.SDK_INT<21){
                Launch.this.finishAffinity();
            } else if(Build.VERSION.SDK_INT>=21){
                Launch.this.finishAndRemoveTask();
            }

        }
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        setContentView(R.layout.activity_launch);
        getSupportActionBar().hide();

        final ImageView fire = (ImageView) findViewById(R.id.startvideo);
        fire.setBackgroundResource(R.drawable.dlogo);
        fireAnimation = (AnimationDrawable) fire.getBackground();
        fireAnimation.start();

        thread = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(3700);
                } catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(Launch.this,MainActivity.class));
                    finish();
                }

            }
        };

        new Handler().postDelayed(new Runnable() {
            public void run() {
                thread.start();
            }
        }, 500);






    }





}
