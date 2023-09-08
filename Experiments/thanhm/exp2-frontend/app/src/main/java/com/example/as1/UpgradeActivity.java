package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UpgradeActivity extends AppCompatActivity {
    private static final int UPGRADE_LAYOUT = R.layout.upgrade_activity;

    private Button backBtn;

    GameState gameState = GameState.get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(UPGRADE_LAYOUT);

        backBtn.setOnClickListener(this::switchToMain);
    }

    private void switchToMain(View view) {
        Intent intent = new Intent(UpgradeActivity.this, MainActivity.class);
        startActivity(intent);
    }
}