package com.example.two_screen_switch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;
    private Button ButtontoA;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textView);
        ButtontoA= findViewById(R.id.ButtontoA);
        Intent intent = getIntent();
        if (intent != null) {
            String textFromScreenA = intent.getStringExtra("COMSClass");
            if (textFromScreenA != null) {
                textView.setText("Text: " + textFromScreenA);
            }
        }

        Button ButtontoA = findViewById(R.id.ButtontoA);
        ButtontoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
