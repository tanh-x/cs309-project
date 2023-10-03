package com.example.as1;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Table_Schedule extends AppCompatActivity {

    private TextView textView;
    private Button Butt_to_DashBoard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_schedule);

        textView = findViewById(R.id.textView);
        Butt_to_DashBoard= findViewById(R.id.Butt_to_DashBoard);
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        Intent intent = getIntent();
        if (intent != null) {
            String textFromScreenA = intent.getStringExtra("COMSClass");
            if (textFromScreenA != null) {
                textView.setText("Text: " + textFromScreenA);
            }
        }

        Button Butt_to_DashBoard = findViewById(R.id.Butt_to_DashBoard);
        Butt_to_DashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Table_Schedule.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Get a reference to the TableLayout

        // Define the number of rows and columns
        int numRows = 8;
        int numCols = 9;

        // Populate the TableLayout with numbers from 1 to 72
        int number = 1;
        for (int row = 0; row < numRows; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            ));

            for (int col = 0; col < numCols; col++) {
                TextView textView = new TextView(this);
                textView.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                ));
                textView.setGravity(android.view.Gravity.CENTER);
                textView.setText(String.valueOf(number));
                tableRow.addView(textView);
                number++;
            }

            tableLayout.addView(tableRow);
        }
    }
}

