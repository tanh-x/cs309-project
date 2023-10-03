package com.example.as1;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RegisterScreen extends AppCompatActivity {

    String username;
    String userEmail;
    String userPW;

    Button back;

    Button submit;

    EditText name;
    EditText email;
    EditText password;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //back = findViewById(R.id.back);
       // submit = findViewById(R.id.submit);

       // name = (EditText) findViewById(R.id.name);
       // email= (EditText) findViewById(R.id.email_id);
      //  password =  (EditText) findViewById(R.id.password);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RegisterScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                username = name.getText().toString();
                userPW = password.getText().toString();
                userEmail = email.getText().toString();

                showToast(username);
                showToast(userPW);
                showToast(userEmail);


            }
        });


    }
    private void showToast(String text)
    {
        Toast.makeText(RegisterScreen.this,text,Toast.LENGTH_SHORT).show();
    }

}
