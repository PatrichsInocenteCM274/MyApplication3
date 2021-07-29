package com.example.myapplicationglv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplicationglv2.databinding.ActivityMainBinding;
import com.example.myapplicationglv2.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setView();
    }

    private void setView() {

        binding.btnPlay.setOnClickListener(v->{
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
        });


    }
}