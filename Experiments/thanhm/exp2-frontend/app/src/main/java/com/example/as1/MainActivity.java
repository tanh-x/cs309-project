package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int MAIN_LAYOUT = R.layout.activity_main;
    private Button cookieClickBtn;
    private Button upgradeBtn;
    private TextView cookieText;
    private TextView rateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(MAIN_LAYOUT);

        cookieClickBtn = findViewById(R.id.cookieClickBtn);
        upgradeBtn = findViewById(R.id.upgradeBtn);
        cookieText = findViewById(R.id.cookieText);
        rateText = findViewById(R.id.rateText);

        upgradeBtn.setOnClickListener((View v) -> {
            Intent intent = new Intent(MainActivity.this, UpgradeActivity.class);
            startActivity(intent);
        });
    }

    private void addCookie(int n) {
        cookieText.setText();
    }
}