package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UpgradeActivity extends AppCompatActivity {

    Button increaseBtn;
    Button backBtn;
    TextView numberTxt;

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        increaseBtn = findViewById(R.id.increaseBtn);
        backBtn = findViewById(R.id.backBtn);
        numberTxt = findViewById(R.id.number);

        increaseBtn.setOnClickListener((View v) -> {
            numberTxt.setText(String.valueOf(++counter));
        });


        backBtn.setOnClickListener((View v) -> {
            Intent intent = new Intent(UpgradeActivity.this, MainActivity.class);
            startActivity(intent);
        });


    }
}