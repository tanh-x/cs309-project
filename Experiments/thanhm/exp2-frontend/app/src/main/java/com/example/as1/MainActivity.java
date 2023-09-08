package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

    private GameState gameState = GameState.get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(MAIN_LAYOUT);

        cookieClickBtn = findViewById(R.id.cookieClickBtn);
        upgradeBtn = findViewById(R.id.upgradeBtn);
        cookieText = findViewById(R.id.cookieText);
        rateText = findViewById(R.id.rateText);

        cookieClickBtn.setOnClickListener(this::addCookie);

        upgradeBtn.setOnClickListener(this::switchToUpgrade);
    }

    @SuppressLint("SetTextI18n")
    private void addCookie(View view) {
        gameState.cookieClickAction();
        cookieText.setText(gameState.getCookieCount() + " cookies");
    }

    private void switchToUpgrade(View view) {
        System.out.println(view.getClass().getCanonicalName());
        Intent intent = new Intent(MainActivity.this, UpgradeActivity.class);
        startActivity(intent);
    }
}