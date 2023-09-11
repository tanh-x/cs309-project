package com.example.two_screen_switch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button switchButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        switchButton = findViewById(R.id.switchButton);

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get text from TextView
                String textFromScreenA = textView.getText().toString();

                // Start Screen B and pass data
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("textFromScreenA", textFromScreenA);
                startActivity(intent);
            }
        });
    }
}
