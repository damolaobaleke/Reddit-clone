package com.tti.wavehealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.tti.wavehealth.views.listings.ListingActivity;

public class MainActivity extends AppCompatActivity {
    ImageView redditLogoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        redditLogoImage = findViewById(R.id.splashImage);

        redditLogoImage.animate().alpha(1.0f).setStartDelay(500);
        redditLogoImage.animate().scaleX(1.2f).scaleY(1.2f).start();

        Handler handler = new Handler();

        Runnable run = new Runnable() {
            @Override
            public void run() {
                startListingActivity();
            }
        };

        handler.postDelayed(run, 3500);

    }


    private void startListingActivity(){
        Intent intent = new Intent(this, ListingActivity.class);
        startActivity(intent);
    }
}