package com.example.two_screen_switch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        if (intent != null) {
            String textFromScreenA = intent.getStringExtra("textFromScreenA");
            if (textFromScreenA != null) {
                textView.setText("Text from Screen A: " + textFromScreenA);
            }
        }
    }
}
