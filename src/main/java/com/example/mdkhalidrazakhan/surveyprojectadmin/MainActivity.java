package com.example.mdkhalidrazakhan.surveyprojectadmin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
Animation ZoomOut;
ImageView imageView;
    private static int SPLASH_TIME = 2000; //This is 2 seconds


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    imageView=(ImageView)findViewById(R.id.splashScreenLogo);
    ZoomOut= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout);
    imageView.setVisibility(View.VISIBLE);
    imageView.startAnimation(ZoomOut);
    //check for network connectivity
        ConnectivityManager connectivityManager= (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager.getActiveNetworkInfo() !=null && connectivityManager.getActiveNetworkInfo().isConnected() &&connectivityManager.getActiveNetworkInfo().isConnected()    )
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent loginActivity= new Intent(getApplicationContext() , Login.class);
                    startActivity(loginActivity);
                    finish();

                }
            },SPLASH_TIME);
        }
        else {
            //take user to network missing page
            Intent gotoNetworkError= new Intent(getApplicationContext(), NoNetwork.class);
           // gotoNetworkError.setex
            gotoNetworkError.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            gotoNetworkError.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(gotoNetworkError);
            finish();

        }
        //splash Screen



    }
}
